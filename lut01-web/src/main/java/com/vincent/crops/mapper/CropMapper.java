package com.vincent.crops.mapper;


import com.vincent.crops.pojo.Crop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CropMapper {

    //1、获取所有commodity
    List<Crop> selectAll();

    Crop selectByCropId(int CropId);


}