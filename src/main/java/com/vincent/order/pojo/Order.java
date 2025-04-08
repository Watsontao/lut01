package com.vincent.order.pojo;

import com.vincent.commodity.pojo.Commodity;
import com.vincent.user.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;

    private int userId;

    private int commodityId;

    private int totalPrice;

    String orderNo;

    //用户和订单是一对多
    private User user;

    //商品和订单是一对多
    private List<Commodity> commodities;


}