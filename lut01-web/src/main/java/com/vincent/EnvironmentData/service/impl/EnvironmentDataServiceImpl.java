// EnvironmentDataServiceImpl.java
package com.vincent.EnvironmentData.service.impl;

import com.vincent.EnvironmentData.mapper.EnvironmentDataMapper;
import com.vincent.EnvironmentData.pojo.EnvironmentData;
import com.vincent.EnvironmentData.service.EnvironmentDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnvironmentDataServiceImpl implements EnvironmentDataService {

    @Resource
    private EnvironmentDataMapper environmentDataMapper;

    @Override
    public List<EnvironmentData> getHistoryByGreenhouseId(Integer greenhouseId) {
        return environmentDataMapper.selectHistoryByGreenhouseId(greenhouseId);
    }

    @Override
    public void insertEnvironmentData(EnvironmentData data) {
        environmentDataMapper.insert(data);
    }
}