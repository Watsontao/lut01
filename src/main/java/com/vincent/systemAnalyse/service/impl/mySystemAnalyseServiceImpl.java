package com.vincent.systemAnalyse.service.impl;

import com.vincent.systemAnalyse.mapper.myTempSystemMapper;
import com.vincent.systemAnalyse.pojo.MyAnalyseOption;
import com.vincent.systemAnalyse.pojo.MyTempSystem;
import com.vincent.systemAnalyse.pojo.SystemResult;
import com.vincent.systemAnalyse.service.mySystemAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-29-9:20
 */
@Service
public class mySystemAnalyseServiceImpl implements mySystemAnalyseService {
    @Autowired
    private myTempSystemMapper tempSystemMapper;

    @Override
    public List<SystemResult> analyseData(List<MyTempSystem> mySystemList, MyAnalyseOption option) {
        tempSystemMapper.insertSystemData(mySystemList);
        List<SystemResult> systemResultList = tempSystemMapper.analyseData(option);
        tempSystemMapper.deleteSystemData();
        return systemResultList;
    }
}
