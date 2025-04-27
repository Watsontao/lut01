// EnvironmentDataController.java
package com.vincent.EnvironmentData.controller;

import com.vincent.EnvironmentData.pojo.EnvironmentData;
import com.vincent.EnvironmentData.service.EnvironmentDataService;
import com.vincent.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/environment")
public class EnvironmentDataController {

    @Resource
    private EnvironmentDataService environmentDataService;

    @GetMapping("/history/{greenhouseId}")
    public Result getHistory(@PathVariable Integer greenhouseId) {
        List<EnvironmentData> list = environmentDataService.getHistoryByGreenhouseId(greenhouseId);
        return Result.success(list);
    }

}