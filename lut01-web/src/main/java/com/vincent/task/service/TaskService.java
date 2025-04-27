package com.vincent.task.service;

import com.vincent.task.pojo.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAll();

    List<Task> list();                     // 全部（带姓名）

    void saveOrUpdate(Task task);          // 新增 / 修改

    void delete(Integer id);

    void deleteBatch(List<Integer> ids);


    public List<Task> listByUser(Integer userId);  //harmonyOS专用

    void finish(Integer id); //harmonyOS专用

}
