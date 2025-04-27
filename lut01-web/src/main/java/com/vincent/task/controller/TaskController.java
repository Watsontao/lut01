package com.vincent.task.controller;

import com.vincent.common.Result;
import com.vincent.task.pojo.Task;
import com.vincent.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    /** 列表（带用户名） */
    @GetMapping("/getAll")
    public Result getAll() {
        return Result.success(taskService.list());
    }

    /** 新增 / 编辑 */
    @PostMapping("/save")
    public Result save(@RequestBody Task task) {
        taskService.saveOrUpdate(task);
        return Result.success();
    }

    /** 删除单条 */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        taskService.delete(id);
        return Result.success();
    }

    /** 批量删除 */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        taskService.deleteBatch(ids);
        return Result.success();
    }
}
