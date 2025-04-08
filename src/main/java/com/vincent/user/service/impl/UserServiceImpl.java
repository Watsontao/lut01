package com.vincent.user.service.impl;


import com.vincent.user.mapper.UserMapper;
import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-20-17:21
 */

@Service //如果没有加上这个注解，Spring容器就不会将其识别为一个服务类。
public class UserServiceImpl implements UserService {
    /*
        只需要把MyUserMapper取过来即可，java实例化类都是new，
        加入一个注解@Autowired帮助我们实现实例化
         */
    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        //insert的结果，如果成功返回的是1(这个1是插入的条数)，失败返回的是exception，而不是0
        return userMapper.add(user);
    }

    @Override
    public void deleteByUserId(User user) {userMapper.deleteByUserId(user);}

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public User findUserByUserId(int userId) {return userMapper.findUserByUserId(userId);}

    @Override
    public User findUserByNameAndPassword(User user) {
        return userMapper.findUserByNameAndPassword(user);
    }

    @Override
    public int updateByUserId(User user ) {return userMapper.updateByUserId(user);}

}
