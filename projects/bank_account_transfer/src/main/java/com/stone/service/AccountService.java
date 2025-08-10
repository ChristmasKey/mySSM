package com.stone.service;

import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

    @Transactional // 添加事务注解
    void transfer(String out, String in, Double money);
}
