package one.sunny.problem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import one.sunny.problem.entity.Problem;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sunny
 * @since 2021-11-01
 */
@Mapper
@Component
public interface ProblemMapper extends BaseMapper<Problem> {

}
