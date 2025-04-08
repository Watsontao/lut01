package com.vincent.order.service;

import com.vincent.order.pojo.Order;

public interface orderService {

    int insert(Order record);

    int deleteByOrderId(int orderId);


}