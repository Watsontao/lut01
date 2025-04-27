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

  // users
  private int userId;
  private String username;
  private String phone;
  private String password;
  private String role;
  private String gender;
  private String idCard;
  private String address;
  private String createDate;
  private String imageUrl;

  // crops
  private int cropId;
  private int cropUserId;
  private String greenhouseName;
  private String cropName;
  private String plantingDate;
  private String growthCycle;
  private String estimatedHarvestDate;
  private String cropStatus;
  private String cropImageUrl;
  private String cropContent;

  // greenhouses
  private int greenhouseId;
  private String greenhouseLocation;
  private String greenhouseType;
  private double area;
  private String greenhouseStatus;
  private String description;
  private String greenhouseImageUrl;
  private String plants;
  private String videoUrl;
  private String cropMaturity;
  private String expectedHarvestDate;
  private int growDays;
  private boolean irrigationStatus;
  private boolean ventilationStatus;
  private boolean lightingStatus;
  private String greenhouseCreateDate;
  private String supervisor;

  // tasks
  private int taskId;
  private String name;
  private String taskContent;
  private String completed;
  private int assignedUserId;
  private String completeTime;
  private String taskGreenhouseName;
  private String publishDate;
  private String deadline;
  private String taskDescription;

 
}
