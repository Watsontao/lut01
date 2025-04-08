package com.vincent.pay.service;


import com.vincent.pay.pojo.Pay;

public interface payService {

    int insert(Pay pay);

    int deleteByPayId(int payId);
}