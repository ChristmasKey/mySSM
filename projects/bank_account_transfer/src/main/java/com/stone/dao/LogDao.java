package com.stone.dao;

import org.apache.ibatis.annotations.Insert;

public interface LogDao {

    @Insert("insert into t_transfer_log (info, create_time) value (#{info}, NOW())")
    void addLog(String info);
}
