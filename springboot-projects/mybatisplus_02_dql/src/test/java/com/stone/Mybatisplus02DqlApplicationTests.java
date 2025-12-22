package com.stone;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stone.dao.UserDao;
import com.stone.domain.User;
import com.stone.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

	@Test
	void testNullValue() {
		// 模拟查询条件
		UserQuery userQuery = new UserQuery();
		userQuery.setMinAge(20);
		// userQuery.setMaxAge(30);

		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// 只有当查询条件传值非空时，对应的查询条件才会生效
		lambdaQueryWrapper.lt(userQuery.getMaxAge() != null, User::getAge, userQuery.getMaxAge());
		lambdaQueryWrapper.gt(userQuery.getMinAge() != null, User::getAge, userQuery.getMinAge());
		userDao.selectList(lambdaQueryWrapper).forEach(System.out::println);
	}

	@Test
	void testData() {
		// 查询投影：
		// LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		// queryWrapper.select(User::getId, User::getName, User::getAge);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id", "name", "age");
		userDao.selectList(queryWrapper).forEach(System.out::println);

		QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.select("count(*) as count, tel");
		queryWrapper2.groupBy("tel");
		List<Map<String, Object>> maps = userDao.selectMaps(queryWrapper2);
		maps.forEach(System.out::println);
	}

	@Test
	void testCondition() {
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "Tom").eq(User::getPassword, "Jerry");
		User user = userDao.selectOne(lambdaQueryWrapper);
		System.out.println(user);

		LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
		// 范围查询：lt le gt ge between eq
		lambdaQueryWrapper2.between(User::getAge, 20, 30);
		userDao.selectList(lambdaQueryWrapper2).forEach(System.out::println);

		LambdaQueryWrapper<User> lambdaQueryWrapper3 = new LambdaQueryWrapper<>();
		// 模糊查询：like
		lambdaQueryWrapper3.like(User::getName, "Tom");
		userDao.selectList(lambdaQueryWrapper2).forEach(System.out::println);
	}
}
