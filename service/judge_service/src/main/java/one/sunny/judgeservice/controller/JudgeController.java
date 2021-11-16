package one.sunny.judgeservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.sunny.commonutils.R;
import one.sunny.judgeservice.service.JudgeService;
import one.sunny.judgeservice.vo.JudgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("judge_service")
@RestController
@RequestMapping("/judge")
@CrossOrigin
// TODO redis
public class JudgeController {
    // TODO 跨模块注入
    @Autowired
    JudgeService judgeService;

    @ApiOperation("题库判题")
    @PostMapping("/problemSet")
    public R judge(@RequestBody JudgeVo judgeVo){
       return judgeService.judge(judgeVo, 1);
    }
    // TODO 其它模块参数用注入方式
}
