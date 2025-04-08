package com.vincent.pay.mapper;


import com.vincent.pay.pojo.Pay;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface payMapper {
    int insert(Pay pay);

    int deleteByPayId(int payId);
}