package one.sunny.contest.service.impl;

import one.sunny.contest.entity.Contest;
import one.sunny.contest.mapper.ContestMapper;
import one.sunny.contest.service.ContestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunny
 * @since 2021-11-15
 */
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements ContestService {

}
