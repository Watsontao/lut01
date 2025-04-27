package com.vincent.greenhouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vincent.greenhouse.pojo.Greenhouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GreenhouseMapper {

    List<Greenhouse> selectAll();

    Greenhouse selectById(Integer id);

    int insert(Greenhouse g);

    int updateById(Greenhouse g);

    int deleteById(Integer id);

    int deleteBatch(@Param("ids") List<Integer> ids);

    /** toggle 三开关 */
    int toggleStatus(@Param("id") Integer id,
                     @Param("field") String  field);



    //用于harmonyOS
    List<Greenhouse> selectByUserId(Integer userId);

}

