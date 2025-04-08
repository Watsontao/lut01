package com.vincent.user.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.vincent.order.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //24.4.8
    @TableId(type = IdType.ID_WORKER)
    private int userId;

    private String userName;

    private String password;

    private String phone;

    private String gender;

    private int balance;

    private String location;

    private int check;

    //用户和订单是一对多
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

}