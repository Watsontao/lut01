package com.vincent.systemAnalyse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @@author vincent
 * @create2023-12-29-8:48
 * 分析结果类，没有表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SystemResult {
    //这两个属性名由ecahrts模板决定
    private String name;
    private Double value;
    //重写tostring()方法，为了形成json格式的数据


    @Override
    public String toString() {
        return "{" +
                "name:'" + name + '\'' +
                ", value:" + value +
                '}';
    }
}
