package com.vincent.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @TableId(value = "userid", type = IdType.AUTO)
    private int userId;

    private String userName;

    private String password;

    private String role;         // 用户身份，例如 “农民工”、 “管理员”

    private String gender;       // 性别

    private String idCard;       // 身份证号

    private String address;      // 家庭住址

    private String createDate;   // 注册日期，使用 VARCHAR 类型存储
}
