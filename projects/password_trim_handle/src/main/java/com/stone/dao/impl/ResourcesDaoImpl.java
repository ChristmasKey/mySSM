package com.stone.dao.impl;

import com.stone.dao.ResourcesDao;
import org.springframework.stereotype.Repository;

@Repository
public class ResourcesDaoImpl implements ResourcesDao {

    @Override
    public boolean readResources(String url, String password) {
        //模拟密码校验
        return "root".equals(password);
    }
}
