package com.vincent.task.service;


import com.vincent.crops.pojo.Crop;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-10:40
 */
public interface TaskService {

    //1、获取所有Task
    List<Crop> selectAll();

    Crop selectByTaskId(int TaskId);



}
