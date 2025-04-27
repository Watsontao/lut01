package com.vincent.task.service.impl;

import com.vincent.task.mapper.TaskMapper;
import com.vincent.task.pojo.Task;
import com.vincent.task.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper mapper;

    @Override
    public List<Task> getAll() {              // ★ 调用联表
        return mapper.selectAllWithUser();
    }

    @Override
    public List<Task> list() {
        return mapper.selectAllWithUser();
    }

    @Override
    public void saveOrUpdate(Task task) {
        if (task.getTaskId() == null) {
            mapper.insert(task);
        } else {
            mapper.update(task);
        }
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            mapper.deleteBatch(ids);
        }
    }




    //harmonyOS专用
    @Override
    public List<Task> listByUser(Integer userId) {
        return mapper.selectByUserId(userId);
    }

    //harmonyOS专用
    @Override
    public void finish(Integer id) {
        mapper.finishTask(id);
    }

}
