package com.stone.service.impl;

import com.stone.dao.AccountDao;
import com.stone.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        System.out.println(1 / 0); // 模拟异常
        accountDao.inMoney(in, money);
    }
}
