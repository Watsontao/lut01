package com.vincent.EnvironmentData.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentData {

    @TableId(value = "envid", type = IdType.AUTO)
    private int envId;

    private int greenhouseId;  // 大棚名称或编号

    private String recordDate;      // 数据记录日期（VARCHAR 格式）

    private double temperature;      // 温度数据

    private double humidity;         // 湿度数据

    private double soilMoisture;     // 土壤湿度

    private double lightIntensity;   // 光照强度
}
