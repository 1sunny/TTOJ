package one.sunny.judgeservice.service.impl;

import one.sunny.commonutils.DataBaseUtil;
import one.sunny.commonutils.R;
import one.sunny.judgeservice.entity.JudgeBean;
import one.sunny.judgeservice.service.JudgeService;
import one.sunny.judgeservice.vo.JudgeVo;
import one.sunny.judgeservice.vo.ResultVo;
import one.sunny.judgeservice.vo.TestCaseResults;
import one.sunny.problem.entity.Problem;
import one.sunny.problem.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    IProblemService problemService;
    @Autowired
    RestTemplate restTemplate;
    @Value("${judge.host}")
    String judgeHost;
    @Value("${judge.port}")
    String judgePort;
    @Autowired
    ValueOperations opsForValue;

    public R judge(JudgeVo judgeVo, Integer judgeType /* 1->题库, 2->比赛 */){
        String code = judgeVo.getCode();
        String type = judgeVo.getType();
        // TODO 健壮性检查
        Problem problem = null;
        if (judgeType == 1){
            problem = problemService.getById(judgeVo.getMysqlProblemId());
        }else{
            String PREFIX = "contestId:" + String.valueOf(judgeVo.getContestId());
            String key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId",judgeVo.getProblemDisplayId());
            problem = (Problem) opsForValue.get(key);
        }
        if (problem == null || type == null || code == null){
            return R.error();
        }

        // TODO 直接写 提前放容器?
        JudgeBean judgeBean = new JudgeBean(type, code,problem.getTimeLimit(),problem.getMemoryLimit(),problem.getTestCaseDir(),true);
//        String json = JSONObject.toJSONString(judgeBean);
        HttpHeaders headers = new HttpHeaders();
        // TODO

        Map<String, Object> verifyParams = new HashMap<String, Object>();
        verifyParams.put("language_config", judgeBean.getLanguage_config());
        verifyParams.put("max_cpu_time", judgeBean.getMax_cpu_time());
        verifyParams.put("max_memory", judgeBean.getMax_memory());
        verifyParams.put("output", judgeBean.getOutput());
        verifyParams.put("src", judgeBean.getSrc());
        verifyParams.put("test_case_id", judgeBean.getTest_case_id());
        headers.add("X-Judge-Server-Token", "c2333a7e3a607935c67c1e6f6810395decc9f66f592b812aaada7db94ba215d6");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(verifyParams, headers);
        System.out.println("http://"+judgeHost+":"+judgePort+"/judge");
        ResultVo resultVo = restTemplate.postForObject("http://"+judgeHost+":"+judgePort+"/judge", entity, ResultVo.class);
        if (resultVo.getErr() != null){
            return R.error().message(resultVo.getErr());
        }
        List<TestCaseResults> data = resultVo.getData();
        Integer wrongReason = 0;
        Integer timeUse = 0;
        Integer memoryUse = 0;
        Integer codeLength = 0;
        String language = "";
        for(TestCaseResults result : data){
            timeUse = Math.max(timeUse, result.getCpu_time());
            memoryUse = Math.max(memoryUse, result.getMemory());
            if (result.getResult() != 0){
                wrongReason = result.getResult();
                break;
            }
        }
        String message = "";
        if (wrongReason == 0){
            message = "Accepted";
        }else if(wrongReason == -1){
            message = "WrongAnswer";
        }else if(wrongReason == 1){
            message = "TimeLimitExceeded";
        }else if(wrongReason == 2){
            message = "RealTimeLimitExceeded";
        }else if(wrongReason == 3){
            message = "MemoryLimitExceeded";
        }else if(wrongReason == 4){
            message = "RuntimeError";
        }else if(wrongReason == 5){
            message = "SystemError";
        }

        return R.ok()
                .data("judgeResult", message)
                .data("timeUse", timeUse)
                .data("memoryUse", memoryUse)
                .data("codeLength", code.getBytes(StandardCharsets.UTF_8).length)
                .data("language", type);
    }
}
