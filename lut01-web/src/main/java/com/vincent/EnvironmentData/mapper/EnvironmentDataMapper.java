// EnvironmentDataMapper.java
package com.vincent.EnvironmentData.mapper;

import com.vincent.EnvironmentData.pojo.EnvironmentData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EnvironmentDataMapper {
    List<EnvironmentData> selectHistoryByGreenhouseId(Integer greenhouseId);

    void insert(EnvironmentData data);
}