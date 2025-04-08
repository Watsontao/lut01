package com.vincent.category.mapper;

import com.vincent.category.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryMapper {

    List<Category> selectAll();



}