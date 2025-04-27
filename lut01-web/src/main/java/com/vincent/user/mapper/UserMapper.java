package com.vincent.user.mapper;

import com.vincent.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


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
    User findUserByNAP(User user);

    User findUserByPAP(User user);




    //7. 批量删除用户
    void deleteBatch(@Param("ids") List<Integer> ids);

    //8. 根据姓名或者手机号进行模糊查询-
    List<User> searchUsers(@Param("userName") String userName, @Param("phone") String phone);


    List<User> selectByRole(String role);


    // 修改密码 harmonyOS
    int updatePassword(@Param("userId") Integer userId,
                       @Param("newPwd") String newPwd);

}