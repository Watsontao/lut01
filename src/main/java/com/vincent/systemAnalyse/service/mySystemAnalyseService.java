package com.vincent.systemAnalyse.service;

import com.vincent.systemAnalyse.pojo.MyAnalyseOption;
import com.vincent.systemAnalyse.pojo.MyTempSystem;
import com.vincent.systemAnalyse.pojo.SystemResult;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-29-9:19
 */
public interface mySystemAnalyseService {

    List<SystemResult> analyseData(List<MyTempSystem> mySystemList, MyAnalyseOption option);
}
