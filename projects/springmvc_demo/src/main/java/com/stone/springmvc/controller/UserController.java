package com.stone.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller注解：表现层注解，功能与@Component类似，将类交给spring管理
@Controller // 将该类定义成Bean
public class UserController {

    @RequestMapping("/save") //@RequestMapping注解：请求映射，用于建立请求URL和处理请求方法之间的对应关系
    @ResponseBody //@ResponseBody注解：将Controller方法返回值作为响应体返回给客户端
    public String save(String username) {
        System.out.println("springmvc save username: " + username);
        return "{'module':'springmvc save'}";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(String username) {
        System.out.println("springmvc update username: " + username);
        return "{'module':'springmvc update'}";
    }

    @RequestMapping("/select")
    @ResponseBody
    public String select(String username) {
        System.out.println("springmvc select username: " + username);
        return "{'module':'springmvc select'}";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String username) {
        System.out.println("springmvc delete username: " + username);
        return "{'module':'springmvc delete'}";
    }
}
