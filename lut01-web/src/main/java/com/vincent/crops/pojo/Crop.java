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
    private String greenhouseName;
    private String cropName;
    private String plantingDate;
    private String growthCycle;
    private String estimatedHarvestDate;
    private String cropImageUrl;
    private String cropContent;
}
