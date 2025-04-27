package com.vincent.common.service.impl;

import com.vincent.common.mapper.commonMapper;
import com.vincent.common.pojo.CommOpper;
import com.vincent.common.pojo.Common;
import com.vincent.common.service.commonService;
import com.vincent.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-23-15:29
 */
@Service
public class commonServiceImpl implements commonService {


    @Autowired
    private commonMapper commonMapper;

    @Override
    public List<Common> selectAll(String tableNameLowCase) {
        return commonMapper.selectAll(tableNameLowCase);
    }

    @Override
    public void delete(CommOpper opper) {
        commonMapper.delete(opper);
    }

    @Override
    public void update(CommOpper opper) {
        commonMapper.update(opper);
    }

    @Override
    public Common getOneById(CommOpper opper) {
        return commonMapper.getOneById(opper);
    }

    @Override
    public void add(CommOpper opper) {
        commonMapper.add(opper);
    }
};
