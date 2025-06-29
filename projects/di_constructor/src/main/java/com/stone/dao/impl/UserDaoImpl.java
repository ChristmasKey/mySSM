package com.stone.dao.impl;

import com.stone.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public void save() {
        System.out.println("user dao save...");
    }
}
