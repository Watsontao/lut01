package com.vincent.task.mapper;


import com.vincent.crops.pojo.Crop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    //1、获取所有Task
    List<Crop> selectAll();

    Crop selectByTaskId(int TaskId);


}