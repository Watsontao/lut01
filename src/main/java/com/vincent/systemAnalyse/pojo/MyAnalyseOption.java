package com.vincent.systemAnalyse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @@author vincent
 * @create2023-12-29-9:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAnalyseOption {
    //传入hour minute date year
    private String group_name;
    //汇总函数 max  min  count  avg  sum
    private String group_fun;
}
