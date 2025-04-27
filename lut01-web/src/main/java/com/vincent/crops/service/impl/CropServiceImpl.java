package com.vincent.crops.service.impl;

import com.vincent.crops.pojo.Crop;
import com.vincent.crops.mapper.CropMapper;
import com.vincent.crops.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:43
 */

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropMapper cropMapper;

    //1、获取所有Crop
    @Override
    public List<Crop> selectAll() {
        return cropMapper.selectAll();
    }

    //2、根据id获取Crop
    @Override
    public Crop selectByCropId(int CropId) {
        return cropMapper.selectByCropId(CropId);
    }

}
