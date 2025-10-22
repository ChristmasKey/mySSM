package com.stone.controller;

import com.stone.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // 响应页面/跳转页面
    @RequestMapping("/toJumpPage")
    public String toJumpPage() {
        System.out.println("跳转页面");
        return "page.jsp";
    }

    // 响应文本数据
    @RequestMapping("/toText")
    @ResponseBody
    public String toText() {
        System.out.println("返回纯文本数据");
        return "hello,stone";
    }

    // 响应json数据
    @RequestMapping("/toJsonPOJO")
    @ResponseBody
    public User toJsonPOJO() {
        System.out.println("返回json对象数据");
        User user = new User();
        user.setName("stone");
        user.setAge(18);
        return user;
    }

    // 响应json集合数据
    @RequestMapping("/toJsonList")
    @ResponseBody
    public List<User> toJsonList() {
        System.out.println("返回json集合数据");
        List<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setName("stone1");
        user1.setAge(18);
        users.add(user1);
        User user2 = new User();
        user2.setName("stone2");
        user2.setAge(19);
        users.add(user2);
        return users;
    }
}
