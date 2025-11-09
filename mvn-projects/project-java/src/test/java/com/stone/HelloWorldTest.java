package com.stone;

import org.junit.Test;
import org.junit.Assert;

public class HelloWorldTest {
	
	@Test
	public void testHello() {
		HelloWorld hw = new HelloWorld();
		String res = hw.hello("World);
		// 利用断言判断值是否相等
		Assert.assertEquals("Hello World", res);
	}
}