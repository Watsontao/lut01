package com.vincent.systemAnalyse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @@author vincent
 * @create2023-12-29-8:43
 * 记录数据的临时表，表的数据分析之后删除
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTempSystem {

    private String mytime;
    private double myvalue;
}
