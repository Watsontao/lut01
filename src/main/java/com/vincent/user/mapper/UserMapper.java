package com.vincent.user.mapper;

import com.vincent.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

    //1、添加一个用户 参数为user 没有返回值
    int add(User user);

    //2、根据id删除一个用户，参数为id 没有返回值
    void deleteByUserId(User user);

    //3、查询所有的用户，没有参数，返回值为List<User>
    List<User> selectAll();

    //4、更新某一个用户的信息，参数为id，没有返回值
    int updateByUserId(User user);

    //5、根据id查找用户
    User findUserByUserId(int userId);

    //6、根据账号密码查找用户
    User findUserByNameAndPassword(User user);
}