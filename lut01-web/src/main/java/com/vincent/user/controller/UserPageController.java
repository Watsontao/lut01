package com.vincent.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.vincent.common.Constants;
import com.vincent.common.Result;
import com.vincent.enums.ResultCodeEnum;
import com.vincent.exception.CustomException;
import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import com.vincent.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
public class UserPageController {

    @Resource
    private UserService userService;



    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        System.out.println("打印一下登录时输入的user的信息：" + user);
        if (ObjectUtil.isEmpty(user.getUserName()) || ObjectUtil.isEmpty(user.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 根据用户名和密码查找用户
        User loginUser = userService.findUserByNAP(user);
        System.out.println("打印一下查询到的User的信息：" + loginUser);

        if (loginUser == null) {
            return Result.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }

        // 登录成功，生成 token
        String role = loginUser.getRole();
        String token = TokenUtils.createToken(loginUser.getUserId() + "-" + role, loginUser.getPassword());

        // 返回数据：user + token
        Map<String, Object> result = new HashMap<>();
        result.put("user", loginUser);
        result.put("token", token);
        return Result.success(result);
    }

    /**
     * 查询所有用户信息（用于后台首页展示）
     */
    @GetMapping("/getAllUser")
    public Result selectAllUsers() {
        List<User> users = userService.getAllUser();
        return Result.success(users);
    }

    /**
     * 新增用户接口
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        // 参数基础校验
        if (ObjectUtil.isEmpty(user.getUserName()) || ObjectUtil.isEmpty(user.getPhone())
                || ObjectUtil.isEmpty(user.getPassword()) || ObjectUtil.isEmpty(user.getRole())
                || ObjectUtil.isEmpty(user.getGender()) || ObjectUtil.isEmpty(user.getIdCard())
                || ObjectUtil.isEmpty(user.getAddress())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 密码校验：必须为11位数字
//        if (!user.getPassword().matches("\\d{11}")) {
//            return Result.error("密码必须为11位数字");
//        }

        // 性别校验
        if (!user.getGender().equals("男") && !user.getGender().equals("女")) {
            return Result.error("性别必须是\"男\"或\"女\"");
        }

        // 角色校验
        if (!user.getRole().equals("农民工") && !user.getRole().equals("管理员")) {
            return Result.error("角色必须是\"农民工\"或\"管理员\"");
        }

        // 身份证校验：18位，末位为数字或X
        if (!Pattern.matches("\\d{17}[\\dXx]", user.getIdCard())) {
            return Result.error("身份证号格式不正确");
        }

        // 设置创建时间（格式：yyyy-MM-dd HH:mm:ss）
        user.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println("usercontroller打印一下传过来的user信息："+user);

        userService.add(user);
        return Result.success();
    }


    /**
     * 批量删除用户接口
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("请选择要删除的用户");
        }
        // 判断是否包含超级管理员ID
        if (ids.contains(1)) {
            return Result.error("超级管理员用户不允许删除");
        }
        userService.deleteBatch(ids);
        return Result.success();
    }


    /**
     * 根据姓名和/或电话模糊查询用户
     */
    @GetMapping("/search")
    public Result searchUsers(@RequestParam(required = false) String userName,
                              @RequestParam(required = false) String phone) {
        List<User> users = userService.searchUsers(userName, phone);
        return Result.success(users);
    }


    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        if (user.getUserId() == null) {
            return Result.error("用户ID不能为空");
        }
        userService.updateByUserId(user);
        return Result.success();
    }

    /** 只返回“农民工”角色的用户，用于任务指派下拉框 */
    @GetMapping("/getFarmers")
    public Result getFarmers() {
        List<User> farmers = userService.findByRole("farmer");
        return Result.success(farmers);
    }




}
