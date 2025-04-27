package com.vincent.crops.controller;

import com.vincent.common.Result;
import com.vincent.crops.pojo.Crop;
import com.vincent.crops.service.impl.CropServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-9:39
 */
@RestController
@RequestMapping("/api/harmony/crop")
public class CropListHarmonyController {



    @Autowired
    private CropServiceImpl cropService;

    //这里调用所有的接口，返回页面




    /** 获取所有作物 */
    @GetMapping("/getAll")
    public Result getAll() {
        List<Crop> list = cropService.selectAll();
        return Result.success(list);
    }

    /** 根据 ID 查询单个作物 */
    @GetMapping("/getById")
    public Result getById(@RequestParam Integer cropId) {
        Crop crop = cropService.selectByCropId(cropId);
        return Result.success(crop);
    }
}
