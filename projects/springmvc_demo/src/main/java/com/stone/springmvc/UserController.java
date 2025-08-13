package com.stone.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //
public class UserController {

    @RequestMapping("/save")
    @ResponseBody
    public String save(String username) {
        System.out.println("springmvc save username: " + username);
        return "{'module':'springmvc save'}";
    }

    public String update(String username) {
        System.out.println("springmvc update username: " + username);
        return "{'module':'springmvc update'}";
    }

    public String select(String username) {
        System.out.println("springmvc select username: " + username);
        return "{'module':'springmvc select'}";
    }

    public String delete(String username) {
        System.out.println("springmvc delete username: " + username);
        return "{'module':'springmvc delete'}";
    }
}
