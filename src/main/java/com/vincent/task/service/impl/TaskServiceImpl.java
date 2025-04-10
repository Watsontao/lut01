package com.vincent.task.service.impl;

import com.vincent.crops.mapper.CropMapper;
import com.vincent.crops.pojo.Crop;
import com.vincent.task.mapper.TaskMapper;
import com.vincent.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:43
 */

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    //1、获取所有Task
    @Override
    public List<Crop> selectAll() {
        return taskMapper.selectAll();
    }

    //2、根据id获取Task
    @Override
    public Crop selectByTaskId(int TaskId) {
        return taskMapper.selectByTaskId(TaskId);
    }

}
