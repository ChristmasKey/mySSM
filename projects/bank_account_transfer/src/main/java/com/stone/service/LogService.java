package com.stone.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogService {

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 开启事务，并设置事务的传播行为为开启一个新事务
    void log(String out, String in, Double money);
}
