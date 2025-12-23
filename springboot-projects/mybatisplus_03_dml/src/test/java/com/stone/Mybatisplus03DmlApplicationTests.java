package com.stone;

import com.stone.dao.UserDao;
import com.stone.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Mybatisplus03DmlApplicationTests {

    @Resource
    private UserDao userDao;

    @Test
    void testGetAll() {
        userDao.selectList(null).forEach(System.out::println);
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        user.setPassword("zhangsan@stone.com");
        user.setTel("123456789");
        userDao.insert(user);
    }

    @Test
    void testBatchOper() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        // 批量删除
        userDao.deleteBatchIds(list);
        // 批量查询
        userDao.selectBatchIds(list).forEach(System.out::println);
    }

    @Test
    void testLogicDel() {
        userDao.deleteById(1L);
    }

    @Test
    void testOptimisticLock() {
        // User user = userDao.selectById(2L);
        // user.setName("李四");
        // userDao.updateById(user);

        User user = userDao.selectById(2L);  // version = 3
        User user2 = userDao.selectById(2L); // version = 3

        user.setName("StoneAAA");
        userDao.updateById(user);  // version = 4

        user2.setName("StoneBBB");
        userDao.updateById(user2); // 执行update失败，因为id=2L的record的version = 4，而user2的version = 3
    }
}
