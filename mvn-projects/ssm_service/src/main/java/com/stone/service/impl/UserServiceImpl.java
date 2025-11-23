package com.stone.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.dao.UserDao;
import com.stone.domain.User;
import com.stone.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Integer uuid) {
        return userDao.delete(uuid);
    }

    @Override
    public User get(Integer uuid) {
        return userDao.get(uuid);
    }

    @Override
    public PageInfo<User> getAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<User> all = userDao.getAll();
        return new PageInfo<>(all);
    }

    @Override
    public User login(String username, String password) {
        return userDao.getByUserNameAndPwd(username, password);
    }
}
