package com.vincent.greenhouse.controller;

import com.vincent.common.Result;
import com.vincent.greenhouse.pojo.Greenhouse;
import com.vincent.greenhouse.service.GreenhouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/greenhouse")
public class GreenhouseController {

    @Resource
    private GreenhouseService greenhouseService;

    /**
     * 获取所有大棚
     */
    @GetMapping("/getAll")
    public Result getAll() {
        List<Greenhouse> list = greenhouseService.getAll();
        return Result.success(list);
    }

    /**
     * 根据ID查询大棚详情
     */
    @GetMapping("/getById")
    public Result getById(@RequestParam Integer id) {
        Greenhouse greenhouse = greenhouseService.getById(id);
        return Result.success(greenhouse);
    }

    /**
     * 新增或更新大棚
     */
    @PostMapping("/save")
    public Result save(@RequestBody Greenhouse greenhouse) {
        greenhouseService.saveOrUpdate(greenhouse);
        return Result.success();
    }

    /**
     * 删除大棚
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        greenhouseService.delete(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        greenhouseService.deleteBatch(ids);
        return Result.success();
    }

    /** 统一切换开关 */
    @PostMapping("/{id}/toggle/{type}")
    public Result toggleStatus(@PathVariable Integer id,
                               @PathVariable String  type) {
        greenhouseService.toggleStatus(id, type);
        return Result.success();
    }
}
