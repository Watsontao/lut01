package com.vincent.crops.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crop {

    @TableId(value = "cropid", type = IdType.AUTO)
    private int cropId;

    private int userId;  // 标识所属用户ID（无外键约束）

    private String greenhouseName;      // 大棚名称或编号

    private String cropName;            // 作物名称

    private String plantingDate;        // 种植日期（VARCHAR 格式）

    private String growthCycle;         // 生长周期，例如 "90天"

    private String estimatedHarvestDate;// 预计收获日期

    private String status;              // 作物状态，如 "生长中"、 "已收获"
}
