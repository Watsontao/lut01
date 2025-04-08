package com.vincent.order.service.impl;

import com.vincent.order.mapper.OrderMapper;
import com.vincent.order.pojo.Order;
import com.vincent.order.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class orderServiceImpl implements orderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insert(Order order) {
        //返回受影响的行数
        int result = orderMapper.insert(order);
        return result;
    }

    @Override
    public int deleteByOrderId(int orderId) {
        return 0;
    }

}
