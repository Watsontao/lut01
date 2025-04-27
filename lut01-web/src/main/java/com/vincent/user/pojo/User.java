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

    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String phone;
    private String password;
    private String role;
    private String gender;
    private String idCard;
    private String address;
    private String createDate;
    private String imageUrl;
}
