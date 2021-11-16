package one.sunny.judgeservice.service;

import one.sunny.commonutils.R;
import one.sunny.judgeservice.vo.JudgeVo;

public interface JudgeService {
    public R judge(JudgeVo judgeVo, Integer judgeType /* 1->题库, 2->比赛 */);
}
