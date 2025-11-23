package com.stone.service;

import com.github.pagehelper.PageInfo;
import com.stone.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserService {

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    boolean save(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    boolean update(User user);

    /**
     * 删除用户
     * @param uuid
     * @return
     */
    @Transactional(readOnly = false)
    boolean delete(Integer uuid);

    /**
     * 查询单个用户
     * @param uuid
     * @return
     */
    User get(Integer uuid);

    /**
     * 查询所有用户
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> getAll(int page, int size);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
