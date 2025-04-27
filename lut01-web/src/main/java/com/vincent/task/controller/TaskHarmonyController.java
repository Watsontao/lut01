package com.vincent.task.controller;

import com.vincent.common.Result;
import com.vincent.task.pojo.Task;
import com.vincent.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/harmony/task")
public class TaskHarmonyController {

    @Resource
    private TaskService taskService;


    /** 根据登录用户 id 查询任务 */
    @GetMapping("/getByUserId")
    public Result getByUser(@RequestParam Integer userId) {
        System.out.println("执行到了TaskHarmonyController");
        return Result.success(taskService.listByUser(userId));
    }

    @PostMapping("/finish/{id}")
    public Result finish(@PathVariable Integer id){
        System.out.println("执行到了TaskHarmonyController");
        taskService.finish(id);
        return Result.success();
    }

}
