package com.stone.dao;

import com.stone.domain.User;
// import org.apache.ibatis.annotations.Insert;

public interface UserDao {

    // @Insert("insert into t_user_info(user_name, age) value(#{userName}, #{age})")
    void save(User user);
}
