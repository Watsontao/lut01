// EnvironmentDataService.java
package com.vincent.EnvironmentData.service;

import com.vincent.EnvironmentData.pojo.EnvironmentData;
import java.util.List;

public interface EnvironmentDataService {
    List<EnvironmentData> getHistoryByGreenhouseId(Integer greenhouseId);

    void insertEnvironmentData(EnvironmentData data);
}