package one.sunny.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.sunny.problem.entity.Problem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunny
 * @since 2021-11-01
 */
public interface IProblemService extends IService<Problem> {
    List<Problem> getAllProblem();
}
