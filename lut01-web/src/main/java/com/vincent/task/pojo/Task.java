package com.vincent.task.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @TableId(value = "taskId", type = IdType.AUTO)
    private Integer taskId;
    private String name;
    private String taskContent;
    private String completed;
    private int assignedUserId;
    private String completeTime;
    private String taskGreenhouseName;
    private String publishTime;
    private String deadline;
    private String taskDescription;


    /* ★ 新增：只读字段，数据库不需要存，用来放用户名 */
    @TableField(exist = false)
    private String assignedUserName;
}

