package one.sunny.contest.controller;


import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.DataBaseUtil;
import one.sunny.commonutils.R;
import one.sunny.contest.entity.Contest;
import one.sunny.contest.mapper.ContestMapper;
import one.sunny.contest.vo.UserRankVo;
import one.sunny.judgeservice.service.JudgeService;
import one.sunny.judgeservice.vo.JudgeVo;
import one.sunny.problem.entity.Problem;
import one.sunny.problem.service.IProblemService;
import one.sunny.problem.vo.SubmitResultVo;
import one.sunny.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunny
 * @since 2021-11-15
 */
@Api("contest_service")
@RestController
@RequestMapping("/contest")
@CrossOrigin
public class ContestController {
//    TODO private
    @Autowired
    ContestMapper contestMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IProblemService problemService;
    @Autowired
    JudgeService judgeService;
    @Autowired
    ValueOperations opsForValue;
    @Autowired
    ListOperations listOperations;
    @Autowired
    SetOperations setOperations;

    public Long getLongByKey(String key){
        Long result = null;
        Object resultObject = opsForValue.get(key);
        if (resultObject == null){
            return 0L;
        }
        result = ((Integer) resultObject).longValue();
        return result;
    }
//  TODO 带条件查询
    @ApiOperation("获取比赛列表")
    @GetMapping("")
    public R getContestList(){
        List<Contest> contests = contestMapper.selectList(null);
        return R.ok().data("contests", contests);
    }

    @ApiOperation("通过获取具体比赛 -> mysql")
    @GetMapping("{id}")
    public R getContestById(@PathVariable("id") Integer id){
        Contest contest = contestMapper.selectById(id);
        if (contest == null){
            return R.error().message("比赛不存在");
        }
        return R.ok().data("contest", contest);
    }

    @ApiOperation("创建比赛->mysql")
    @PostMapping("create")
    public R createContest(@RequestBody Contest contest){
        int insert = contestMapper.insert(contest);
        if (insert > 0){
            return R.ok().message("创建成功");
        }
        return R.error().message("创建失败");
    }

    @ApiOperation("通过比赛id和题目displayId获取题目 -> redis")
    @GetMapping("{contestId}/problems/{problemDisplayId}")
    public R getProblemByProblemDisplayId(@PathVariable("contestId") Integer contestId,
                             @PathVariable("problemDisplayId") String problemDisplayId){
        if (contestId == null || problemDisplayId == null){
            return R.error().message("比赛id或displayId为空");
        }
        String PREFIX = "contestId:"+String.valueOf(contestId);
        String key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId", problemDisplayId);
        Problem problem = (Problem) opsForValue.get(key);
        return R.ok().data("problem", problem);
    }

    @ApiOperation("通过比赛id给比赛增加题目(id -> mysql) -> redis")
    @PostMapping("{id}/addProblem")
    public R addContestProblemById(@RequestParam("problemId") Integer problemId,
                               @PathVariable("id") Integer contestId){
        if (problemId == null){
            return R.error().message("problemId 为空");
        }
        String PREFIX = "contestId:" + String.valueOf(contestId);
        Problem problem = problemService.getById(problemId);
        Integer currentId = (Integer) opsForValue.get("currentId");
        String problemDisplayId = String.valueOf(currentId);
        String key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId", problemDisplayId);
        opsForValue.set(key, problem);
        key = DataBaseUtil.redisSql(PREFIX, "problemDisplayIds");
        listOperations.leftPush(key, problemDisplayId);
        // TODO 如果displayId已经存在
        opsForValue.increment("currentId");
        return R.ok().message("添加成功");
    }
    @ApiOperation("通过比赛id获取所有比赛题目 -> redis")
    @GetMapping("{id}/problems")
    public R getProblemsByContestId(@PathVariable("id") Integer contestId){
        if (contestId == null){
            return R.error().message("id为空");
        }
        String PREFIX = "contestId:" + String.valueOf(contestId);
        String key = DataBaseUtil.redisSql(PREFIX, "problemDisplayIds");
        List<String> problemDisplayIds = listOperations.range(key, 0, -1);
        System.out.println(problemDisplayIds);
        List<Problem> problems = new ArrayList<>();
        for (String  problemDisplayId: problemDisplayIds){
            key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId", problemDisplayId);
            Problem problem = (Problem) opsForValue.get(key);
            problems.add(problem);
        }
        return R.ok().data("problems", problems);
    }

    @ApiOperation("用户在比赛中提交代码")
    @PostMapping("contestSubmit")
    public R contestSubmit(@RequestBody JudgeVo judgeVo,
                    HttpSession httpSession){
//       用户提交时间
        Date submitTime = new Date();
//        判题
        R judgeResult = judgeService.judge(judgeVo, 2);
//        通过session得到用户
        User user = (User) httpSession.getAttribute("user");
        if (user == null){
//            return R.error().message("未登录");
            user = new User(2,"tutu","123","email","no",123);
        }
        Integer contestId = judgeVo.getContestId();
        String problemDisplayId = judgeVo.getProblemDisplayId();
//        封装提交Vo
        Map<String, Object> resultData = judgeResult.getData();
        SubmitResultVo submitResultVo = new SubmitResultVo(
                99999L/*displayId*/,
                user.getUsername(),
                problemDisplayId,
                (String) resultData.get("judgeResult"),
                (Integer) resultData.get("timeUse"),
                (Integer) resultData.get("memoryUse"),
                (Integer) resultData.get("codeLength"),
                (String) resultData.get("language"),
                submitTime
        );
//        通用前缀
        String prefix = "contestId:" + String.valueOf(contestId);
//        将该用户加入set, 所以历史排行榜里的用户名不会随用户修改信息而改变
        String key = DataBaseUtil.redisSql(prefix, "users");
        Long add = setOperations.add(key, user);
//        加入比赛提交里(总)
        key = DataBaseUtil.redisSql(prefix, "submissions");
        listOperations.leftPush(key, submitResultVo);
//        加入用户在该题的提交
        key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "problemDisplayId", problemDisplayId, "submissions");
        listOperations.leftPush(key, submitResultVo);
//        加入用户这场比赛总的提交
        key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "submissions");
        listOperations.leftPush(key, submitResultVo);
        Long submitPenalty = 20L * 60 * 60;
//        处理判题结果
        if (resultData.get("judgeResult") == "Accepted"){
//            是否该题一血
            key = DataBaseUtil.redisSql(prefix, "problemDisplayId", problemDisplayId, "firstBloodUsername");
            String username = (String) opsForValue.get(key);
            if (username == null){
                opsForValue.set(key, user.getUsername());
            }
            Contest contest = contestMapper.selectById(contestId);
//            是否该用户第一次通过该题
            key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "problemDisplayId",problemDisplayId,"firstAcTime");
            Date acTime = (Date) opsForValue.get(key);
            if (acTime == null){
                opsForValue.set(key, submitTime);
//            加题号进set
                key = DataBaseUtil.redisSql(prefix,"username", user.getUsername(),  "AcProblemDisplayIds");
                listOperations.leftPush(key, problemDisplayId);
//            罚时加上时间差 + wa的次数 * wa的代价
                Date startTime = contest.getStartTime();
                Long additionMs = DateUtil.betweenMs(startTime, submitTime);
                key = DataBaseUtil.redisSql(prefix,"username", user.getUsername(), "problemDisplayId",problemDisplayId, "waTimes");
                Long waTimes = (Long) opsForValue.get(key);
                if (waTimes == null){
                    waTimes = 0L;
                }
                additionMs += waTimes * submitPenalty;
                key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "penalty");
                Long penalty = this.getLongByKey(key);
                if (penalty == null){
                    penalty = 0L;
                }
                opsForValue.set(key, penalty + additionMs);
            }
            /*
            TODO 字段增加 penalty   username不允许重复
             */
        }else{
//            该用户之前是否通过此题
            key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "problemDisplayId",problemDisplayId,"firstAcTime");
            Date acTime = (Date) opsForValue.get(key);
            if (acTime == null){
//            该题wa次数++
                key = DataBaseUtil.redisSql(prefix, "username",user.getUsername(), "problemDisplayId",problemDisplayId, "wa");
                opsForValue.increment(key);
            }
        }
        return judgeResult;
    }
    @ApiOperation("通过id获取比赛排名")
    @GetMapping("rankings/{contestId}")
    public R getRankings(@PathVariable("contestId") Integer contestId){
        /*
        参加比赛用户的 ac, penalty, 每题wa的次数,每题的提交情况
         */
        String PREFIX = "contestId:" + String.valueOf(contestId);
        String key = DataBaseUtil.redisSql(PREFIX, "users");
        Set<User> users = setOperations.members(key);
        key = DataBaseUtil.redisSql(PREFIX, "problemDisplayIds");
//        根据比赛id获得所有题目
        List<String> problemDisplayIds = listOperations.range(key, 0, -1);
        List<Problem> problems = new ArrayList<>();
        for (String problemDisplayId : problemDisplayIds){
            key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId", problemDisplayId);
            Problem problem = (Problem) opsForValue.get(key);
            problems.add(problem);
        }
        List<UserRankVo> rankList = new ArrayList<>();

        for (User user : users){
//            获得该用户所有通过题目的displayId
            key = DataBaseUtil.redisSql(PREFIX, "username", user.getUsername(), "AcProblemDisplayIds");
            List<String> acProblemDisplayIds = listOperations.range(key, 0, -1);
            Integer acCnt = acProblemDisplayIds.size();
//            获得该用户罚时
            key = DataBaseUtil.redisSql(PREFIX, "username", user.getUsername(), "penalty");
            System.out.println(key);
            Long penalty = this.getLongByKey(key);
            Map<String, Boolean> isFirstAcUser = new HashMap<>();
            Map<String, Date> firstAcTime = new HashMap<>();
            Map<String, Integer> waTimes = new HashMap<>();
            for(Problem problem : problems){
                String problemDisplayId = problem.getDisplayId();
//            对每个题目判断是否首次通过用户
                key = DataBaseUtil.redisSql(PREFIX, "problemDisplayId", problemDisplayId, "firstBloodUsername");
                String firstAcUsername = (String) opsForValue.get(key);
                isFirstAcUser.put(problem.getDisplayId(), user.getUsername().equals(firstAcUsername));
//            对每个题目得到该用户第一次通过时间
                key = DataBaseUtil.redisSql(PREFIX, "username", user.getUsername(), "problemDisplayId", problemDisplayId, "firstAcTime");
                Date firstAcDate = (Date) opsForValue.get(key);
                firstAcTime.put(problemDisplayId, firstAcDate);
//              每个题目的wa次数
                key = DataBaseUtil.redisSql(PREFIX, "username",user.getUsername(), "problemDisplayId",problemDisplayId, "wa");
                Integer waTime = (Integer) opsForValue.get(key);
                if (waTime == null){
                    waTime = 0;
                }
                waTimes.put(problemDisplayId, waTime);
            }

            UserRankVo userRankVo = new UserRankVo(user, acCnt, penalty, isFirstAcUser, firstAcTime, waTimes);
            rankList.add(userRankVo);
        }
        Collections.sort(rankList);
        Collections.sort(problemDisplayIds);
        return R.ok().data("rankList", rankList).data("problemDisplayIds", problemDisplayIds);
    }

}
/*
将总罚时和每题罚时(还要在单元格显示wa的次数)转为分钟
表格每个displayId上显示ac/总提交
比赛结束后还可以点击打开代码
 */

// TODO 写进service
