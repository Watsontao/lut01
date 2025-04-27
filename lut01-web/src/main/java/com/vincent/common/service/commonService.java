package com.vincent.common.service;

import com.vincent.common.pojo.CommOpper;
import com.vincent.common.pojo.Common;
import com.vincent.user.pojo.User;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-23-15:28
 */
public interface commonService {

    List<Common> selectAll(String table_name);

    void delete(CommOpper opper);

    void add(CommOpper opper);

    Common getOneById(CommOpper opper);

    void update(CommOpper opper);

}
