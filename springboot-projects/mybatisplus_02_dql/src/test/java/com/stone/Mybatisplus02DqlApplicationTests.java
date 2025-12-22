package com.stone;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stone.dao.UserDao;
import com.stone.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Mybatisplus02DqlApplicationTests {

	@Resource
	private UserDao userDao;

	@Test
	void testGetAll() {
		// 方式一：创建条件构造器
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		// 构造查询条件：age < 25
		wrapper.lt("age", 25);
		userDao.selectList(wrapper).forEach(System.out::println);

		// 方式二：Lambda格式按条件查询
		QueryWrapper<User> lambdaWrapper = new QueryWrapper<>();
		lambdaWrapper.lambda().lt(User::getAge, 25);
		userDao.selectList(lambdaWrapper).forEach(System.out::println);

		// 方式三：Lambda格式按条件查询（推荐）
		LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
		// lambdaQueryWrapper2.lt(User::getAge, 25);
		// and 方式连接多条件 链式编程
		// lambdaQueryWrapper2.lt(User::getAge, 30).gt(User::getAge, 10);
		// or  方式连接多条件 链式编程
		// lambdaQueryWrapper2.lt(User::getAge, 10).or().gt(User::getAge, 30);
		userDao.selectList(lambdaQueryWrapper2).forEach(System.out::println);
	}
}
