package com.stone.service.impl;

import com.stone.domain.User;
import com.stone.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public void save(User user) {
        System.out.println("user service...");
    }
}
