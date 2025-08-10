package com.stone.service.impl;

import com.stone.dao.LogDao;
import com.stone.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public void log(String out, String in, Double money) {
        logDao.addLog("【转账操作】转出方：" + out + "，转入方：" + in + "，金额：" + money);
    }
}
