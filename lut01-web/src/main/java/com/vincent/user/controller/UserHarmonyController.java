package com.vincent.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.vincent.common.Result;
import com.vincent.enums.ResultCodeEnum;
import com.vincent.user.pojo.ChangePwdDTO;
import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import com.vincent.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/harmony/user")
public class UserHarmonyController {

    @Resource
    private UserService userService;

    /**
     * 鸿蒙客户端登录接口（用户名+密码）
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (ObjectUtil.isEmpty(user.getPhone()) || ObjectUtil.isEmpty(user.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        System.out.println("【登录请求】手机号 = " + user.getPhone());

        // 查询用户（by phone + password）
        User loginUser = userService.findUserByPAP(user);

        if (loginUser == null) {
            return Result.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }

        // 登录成功，生成 token
        String role = loginUser.getRole();
        String token = TokenUtils.createToken(
                loginUser.getUserId() + "-" + role,
                loginUser.getPassword()   // ★ 可换成固定盐值提升安全
        );

        // 为避免密码泄露，返回前清空 password 字段
        loginUser.setPassword(null);

        // 返回 user + token
        Map<String, Object> data = new HashMap<>();
        data.put("user", loginUser);
        data.put("token", token);

        return Result.success(data);
    }



    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody Map<String, String> body) {
        String oldPwd = body.get("oldPassword");
        String newPwd = body.get("newPassword");
        if (oldPwd == null || newPwd == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // 从 Token 中获取当前用户
        User current = TokenUtils.getCurrentUser();
        if (current == null) {
            return Result.error(ResultCodeEnum.NOT_LOGIN);
        }
        boolean ok = userService.changePassword(current.getUserId(), oldPwd, newPwd);
        if (!ok) {
            return Result.error(ResultCodeEnum.PASSWORD_ERROR);
        }
        return Result.success();
    }
}
