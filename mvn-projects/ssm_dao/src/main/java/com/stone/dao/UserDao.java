package com.stone.dao;

import com.stone.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 删除用户
     * @param uuid
     * @return
     */
    boolean delete(Integer uuid);

    /**
     * 查询单个用户
     * @param uuid
     * @return
     */
    User get(Integer uuid);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getAll();

    /**
     * 根据用户名和密码查询用户
     * @param userName 用户名
     * @param pwd 密码
     * @return
     */
    User getByUserNameAndPwd(@Param("userName") String userName, @Param("pwd") String pwd);
}
