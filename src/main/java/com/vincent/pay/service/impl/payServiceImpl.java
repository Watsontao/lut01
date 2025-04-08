package com.vincent.pay.service.impl;


import com.vincent.pay.mapper.payMapper;
import com.vincent.pay.pojo.Pay;
import com.vincent.pay.service.payService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class payServiceImpl implements payService {

    @Autowired
    private payMapper payMapper;


    @Override
    public int insert(Pay record) {
        return payMapper.insert(record);
    }

    @Override
    public int deleteByPayId(int payId) {

        return payMapper.deleteByPayId(payId);
    }
}
