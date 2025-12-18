# mySpringBoot笔记

![姬子](./images/姬子.jpg)

## 入门案例

SpringBoot框架的设计目的是用来<span style="color:red;">简化Spring应用的初始搭建以及开发过程</span>。

创建项目工程`springboot_01_quickstart`    【[IDEA2025创建SpringBoot项目向导](https://www.jetbrains.com/zh-cn/help/idea/spring-initializr-project-wizard.html)】

![IDEA创建SpringBoot项目1](./images/IDEA创建SpringBoot项目1.png)

---

![IDEA创建SpringBoot项目2](./images/IDEA创建SpringBoot项目2.png)

将项目的目录结构清理成如下状态

![清理后的项目目录结构](./images/清理后的项目目录结构.png)

在`pom.xml`中修改SpringBoot版本号和java版本号

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.6</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.stone</groupId>
    <artifactId>springboot_01_quickstart</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

修改项目的java版本和“Language Level”

![修改项目的java版本](./images/修改项目的java版本.png)

随后创建一个Controller，并启动项目

```java
package com.stone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

}
```

![SpringBoot项目启动运行结果](./images/SpringBoot项目启动运行结果.png)

访问url http://localhost:8080/user/hello

![SpringBoot项目请求结果](./images/SpringBoot项目请求结果.png)

123

https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=91