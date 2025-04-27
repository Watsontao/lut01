package com.vincent.greenhouse.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greenhouse {

    @TableId(value = "greenhouse_id", type = IdType.AUTO)
    private Integer greenhouseId;

    private String greenhouseName;

    private Integer userId;

    private String greenhouseLocation;

    private String greenhouseType;

    private Double area;

    private String status;

    private String description;

    private String imageUrl;

    private String plants;

    private String videoUrl;

    private String cropMaturity;

    private String expectedHarvest;

    private Integer growDays;

    private Boolean irrigationStatus;

    private Boolean ventilationStatus;

    private Boolean lightingStatus;

    private String createTime;

    private String supervisor;
}