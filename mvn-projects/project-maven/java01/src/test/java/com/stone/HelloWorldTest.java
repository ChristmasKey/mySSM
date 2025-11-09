package com.stone;

import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testSayHello() {
        HelloWorld helloWorld = new HelloWorld();
        String res = helloWorld.sayHello("World");
        Assert.assertEquals("Hello World", res);
    }
}
