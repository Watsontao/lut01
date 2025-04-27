package com.vincent.task.mapper;

import com.vincent.task.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {

    /** 带用户名的查询 */
    List<Task> selectAllWithUser();

    Task selectById(Integer id);

    int insert(Task t);
    int update(Task t);
    int deleteById(Integer id);
    int deleteBatch(@Param("ids") List<Integer> ids);


    /** 根据用户 id 查询其任务 */
    List<Task> selectByUserId(@Param("userId") Integer userId);

    int finishTask(Integer id);


}