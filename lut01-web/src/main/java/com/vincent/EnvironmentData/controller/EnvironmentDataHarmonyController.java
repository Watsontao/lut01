// EnvironmentDataController.java
package com.vincent.EnvironmentData.controller;

import com.vincent.EnvironmentData.pojo.EnvironmentData;
import com.vincent.EnvironmentData.service.EnvironmentDataService;
import com.vincent.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/harmony/environment")
public class EnvironmentDataHarmonyController {

    @Resource
    private EnvironmentDataService environmentDataService;

    @GetMapping("/history/{greenhouseId}")
    public Result getHistory(@PathVariable Integer greenhouseId) {
        System.out.println("【Controller层】EnvironmentDataHarmonyController ✅ 收到 greenhouseId = " + greenhouseId);
        List<EnvironmentData> list = environmentDataService.getHistoryByGreenhouseId(greenhouseId);
        return Result.success(list);
    }



}