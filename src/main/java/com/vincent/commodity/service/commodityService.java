package com.vincent.commodity.service;

import com.vincent.commodity.pojo.Commodity;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:40
 */
public interface commodityService {

    //1、获取所有commodity
    List<Commodity> selectAll();

    Commodity selectByCommodityId(int commodityId);



}
