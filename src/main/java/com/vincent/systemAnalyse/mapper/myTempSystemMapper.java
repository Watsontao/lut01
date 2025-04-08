package com.vincent.systemAnalyse.mapper;

import com.vincent.systemAnalyse.pojo.MyAnalyseOption;
import com.vincent.systemAnalyse.pojo.MyTempSystem;
import com.vincent.systemAnalyse.pojo.SystemResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-29-8:45
 */
@Mapper
public interface myTempSystemMapper {
    //插入分析的临时数据
    void insertSystemData(@Param("mysystems") List<MyTempSystem> mysystems);
    //进行汇总分析结果的返回,这里传参是个对象，对象两个参数（小时，分钟），（求和，平均数，最小数）
    List<SystemResult> analyseData(MyAnalyseOption option);
    //把临时表的数据删除，这里删除所有，不需要传送参数
    void deleteSystemData();

}
