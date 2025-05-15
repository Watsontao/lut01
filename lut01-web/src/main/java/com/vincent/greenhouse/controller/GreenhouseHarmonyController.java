package com.vincent.greenhouse.controller;

import com.vincent.common.Result;
import com.vincent.greenhouse.pojo.Greenhouse;
import com.vincent.greenhouse.service.GreenhouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/harmony/greenhouse")
public class GreenhouseHarmonyController {

    @Resource
    private GreenhouseService greenhouseService;

    @GetMapping("/getByUserId")
    public Result getByUser(@RequestParam Integer userId) {
        System.out.println("执行到了GreenhouseHarmonyController");
        List<Greenhouse> list = greenhouseService.getByUserId(userId);
        return Result.success(list);
    }



    /** 统一切换开关 */
    @PostMapping("/{id}/toggle/{type}")
    public Result toggleStatus(@PathVariable Integer id,
                               @PathVariable String  type) {
        greenhouseService.toggleStatus(id, type);
        return Result.success();
    }


}
