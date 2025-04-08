package com.vincent.order.mapper;

import com.vincent.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper {

    int insert(Order order);

    int deleteByOrderId(int orderId);

}