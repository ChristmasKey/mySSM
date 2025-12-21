package com.stone.controller;

import com.stone.domain.Enterprise;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    // 读取配置方式一
    @Value("${lesson}")
    private String lesson;

    @Value("${enterprise.name}")
    private String enterpriseName;

    @Value("${enterprise.employees[0].name}")
    private String employeeName_00;

    // 读取配置方式二
    @Resource
    private Environment environment;

    // 读取配置方式三
    @Resource
    private Enterprise enterprise;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println("id ==> " + id);
        System.out.println("lesson ==> " + lesson);
        System.out.println("enterpriseName ==> " + enterpriseName);
        System.out.println("employeeName_00 ==> " + employeeName_00);
        System.out.println("-----------------------------");
        System.out.println("lesson ==> " + environment.getProperty("lesson"));
        System.out.println("employeeName_01 ==> " + environment.getProperty("enterprise.employees[1].name"));
        System.out.println("-----------------------------");
        System.out.println("enterprise ==> " + enterprise);
        return "Hello, Springboot!";
    }
}
