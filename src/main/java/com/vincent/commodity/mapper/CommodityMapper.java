package com.vincent.commodity.mapper;

import com.vincent.commodity.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
@Mapper
public interface CommodityMapper {

    //1、获取所有commodity
    List<Commodity> selectAll_test();

    Commodity selectByCommodityId(int commodityId);


}