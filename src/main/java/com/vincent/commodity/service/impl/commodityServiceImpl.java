package com.vincent.commodity.service.impl;

import com.vincent.commodity.mapper.CommodityMapper;
import com.vincent.commodity.pojo.Commodity;
import com.vincent.commodity.service.commodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:43
 */

@Service
public class commodityServiceImpl implements commodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    //1、获取所有commodity
    @Override
    public List<Commodity> selectAll() {
        System.out.println("打印一下从gaussdb查询的所有的商品："+commodityMapper.selectAll_test());
        return commodityMapper.selectAll_test();
    }

    //2、根据id获取commodity
    @Override
    public Commodity selectByCommodityId(int commodityId) {
        return commodityMapper.selectByCommodityId(commodityId);
    }

}
