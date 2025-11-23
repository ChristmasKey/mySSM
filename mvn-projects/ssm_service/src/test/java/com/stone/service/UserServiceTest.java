package com.stone.service;

import com.stone.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-service.xml", "classpath*:applicationContext-dao.xml"})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testSave() {
        User user = new User();
        user.setUserName("Stone");
        user.setPassword("1234");
        user.setRealName("张三");
        user.setGender(1);
        user.setBirthday(new Date());
        userService.save(user);
    }
}
