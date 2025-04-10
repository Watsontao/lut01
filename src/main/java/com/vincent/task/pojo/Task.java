package com.vincent.task.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @TableId(value = "taskid", type = IdType.AUTO)
    private int taskId;

    private String taskContent;      // 任务内容描述

    private int assignedTo;          // 分配任务的用户ID（不做外键约束）

    private String taskStatus;       // 任务状态，如 "待处理"、 "已完成"

    private String startDate;        // 任务开始日期（VARCHAR 格式）

    private String completionDate;   // 任务完成日期（VARCHAR 格式）
}

