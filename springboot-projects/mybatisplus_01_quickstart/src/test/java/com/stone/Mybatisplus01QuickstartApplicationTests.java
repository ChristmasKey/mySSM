package com.stone;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stone.dao.UserDao;
import com.stone.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Mybatisplus01QuickstartApplicationTests {

    @Resource
    private UserDao userDao;

    @Test
    void testGetAll() {
        userDao.selectList(null).forEach(System.out::println);
    }

    @Test
    void testSave() {
        User user = new User();
        user.setName("张三111");
        user.setAge(20);
        user.setPassword("123456");
        user.setTel("123456789");
        int insert = userDao.insert(user);
        System.out.println("insert结果: " + insert);
    }

    @Test
    void testDelete() {
        int i = userDao.deleteById(1L);
        System.out.println("delete结果: " + i);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(2L);
        user.setName("Stone");
        int i = userDao.updateById(user);
        System.out.println("update结果: " + i);
    }

    @Test
    void testGetById() {
        User user = userDao.selectById(2L);
        System.out.println("user结果: " + user);
    }

    @Test
    void testGetByPage() {
        IPage<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userDao.selectPage(page, null);
        System.out.println("当前页码值: " + userIPage.getCurrent());
        System.out.println("每页显示数: " + userIPage.getSize());
        System.out.println("总页码数: " + userIPage.getPages());
        System.out.println("总记录数: " + userIPage.getTotal());
        System.out.println("userIPage结果: ");
        userIPage.getRecords().forEach(System.out::println);
    }

}
