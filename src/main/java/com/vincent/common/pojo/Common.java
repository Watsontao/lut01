package com.vincent.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于封装毕设项目数据库所有表的信息：
 * 包括用户表（users）、农作物表（crops）、环境数据表（environment_data）及任务管理表（tasks）。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Common {

  // 用户表信息
  private int userId;          // 用户ID
  private String userName;     // 用户名
  private String passWord;     // 密码
  private String role;         // 用户角色（如农民、管理员等）
  private String gender;       // 性别
  private String idCard;       // 身份证号
  private String address;      // 家庭住址
  private String createDate;   // 注册或创建日期

  // 农作物表信息
  private int cropId;                   // 作物ID
  private int cropUserId;               // 农作物所属的用户ID
  private String cropGreenhouseName;    // 大棚名称或编号（农作物所属大棚）
  private String cropName;              // 作物名称
  private String plantingDate;          // 种植日期
  private String growthCycle;           // 生长周期，如 "90天"
  private String estimatedHarvestDate;  // 预计收获日期
  private String cropStatus;            // 作物状态，如 "生长中"、"已收获"

  // 环境数据表信息
  private int envId;                   // 环境数据记录ID
  private String envGreenhouseName;    // 大棚名称或编号（针对环境监测）
  private String recordDate;           // 数据记录日期
  private Float temperature;           // 温度
  private Float humidity;              // 湿度
  private Float soilMoisture;          // 土壤湿度
  private Float lightIntensity;        // 光照强度

  // 任务管理表信息
  private int taskId;               // 任务ID
  private String taskContent;       // 任务内容描述
  private int assignedTo;           // 分配任务的用户ID
  private String taskStatus;        // 任务状态，如 "待处理"、"已完成"
  private String startDate;         // 任务开始日期
  private String completionDate;    // 任务完成日期

  public static void main(String[] args) {
    Common common = new Common();
    System.out.println(common);
  }
}
