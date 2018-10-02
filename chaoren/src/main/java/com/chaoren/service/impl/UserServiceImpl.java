package com.chaoren.service.impl;

import com.chaoren.model.User;
import com.chaoren.respositories.UserRespostiories;
import com.chaoren.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with Date:2018/10/2 Time:15:51
 * author:LiChao
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespostiories userRespostiories;

    @Override
    public int addOneUser(User user) {

        return userRespostiories.addUserOne(user);
    }
}
