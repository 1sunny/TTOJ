package one.sunny.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import one.sunny.commonutils.R;
import one.sunny.user.entity.User;
import one.sunny.user.mapper.UserMapper;
import one.sunny.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunny
 * @since 2021-11-13
 */
@Slf4j
@Api("user_service")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public R login(@RequestBody User user, HttpSession httpSession){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                    .eq("password", user.getPassword());
        User res = userMapper.selectOne(queryWrapper);
        if (res == null){
            return R.error().message("用户名或密码错误");
        }
        httpSession.setAttribute("user", user);
        return R.ok().data("user", res).message("登录成功");
    }
    @PostMapping("/register")
    public R register(@RequestBody User user){
        boolean save = userService.save(user);
        if (save){
            return R.ok().message("注册成功");
        }
        return R.error().message("注册失败");
    }
}

