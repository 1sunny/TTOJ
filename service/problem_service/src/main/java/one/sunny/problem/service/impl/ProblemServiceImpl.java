package one.sunny.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.problem.entity.Problem;
import one.sunny.problem.mapper.ProblemMapper;
import one.sunny.problem.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunny
 * @since 2021-11-01
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {
    @Autowired
    ProblemMapper problemMapper;
// TODO -> is_deleted
    @Override
    public List<Problem> getAllProblem() {
        return problemMapper.selectList(null);
    }
}
