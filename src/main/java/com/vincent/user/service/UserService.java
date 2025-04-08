package com.vincent.user.service;



import com.vincent.user.pojo.User;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-20-17:20
 */
public interface UserService {

    //1、添加一个用户 参数为user 没有返回值
    int add(User user);

    //2、根据id删除一个用户，参数为id 没有返回值
    void deleteByUserId(User user);

    //3、查询所有的用户，没有参数，返回值为List<User>
    List<User> getAllUser();

    //4、更新某一个用户的信息，参数为id，没有返回值
    int updateByUserId(User user);

    //5、根据id查找用户
    User findUserByUserId(int userId);

    //6、根据账号密码查找用户
    User findUserByNameAndPassword(User user);

}
