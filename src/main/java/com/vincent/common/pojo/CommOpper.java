package com.vincent.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommOpper {

    private String tableName;

    private String idColumnName;  //表id的名字

    private int id;

    private Map<String,Object> maps;


}
