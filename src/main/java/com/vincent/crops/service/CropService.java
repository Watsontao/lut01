package com.vincent.crops.service;


import com.vincent.crops.pojo.Crop;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:40
 */
public interface CropService {

    //1、获取所有Crop
    List<Crop> selectAll();

    Crop selectByCropId(int CropId);



}
