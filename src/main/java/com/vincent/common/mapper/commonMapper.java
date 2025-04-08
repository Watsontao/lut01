package com.vincent.common.mapper;

import com.vincent.common.pojo.CommOpper;
import com.vincent.common.pojo.Common;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-23-15:26
 */
@Mapper
public interface commonMapper {

    //显示某个表的数据
    List<Common> selectAll(String tableName);

    //除了更新，还有getOneById
    Common getOneById();

    //删除某个表的数据,  delete from 表名（动态）  where id=?
    void delete(CommOpper opper);

    //增加数据的方法，添加数据后，后面的值是个值对，一个数据对应了一个参数，值对两个类型，类，HashMap
    //这里第二个参数,里面的值个数是变化的,book有book表的几个字段,user有user表的几个字段
    //变化长度的键值对,只能HashMap
    void add(CommOpper opper);

    //除了更新更新，还有getOneById，这里还里传入一个class_name表
    Common getOneById(CommOpper opper);

    //加入更新
    void update(CommOpper opper);


}
