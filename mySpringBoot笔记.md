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

修改项目的**Java版本**和“**Language Level**”

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

<span style="color:red;">此外，我们还可以在Spring的官网创建SpringBoot项目，此处略。</span>



## 快速启动

![SpringBoot项目快速启动](./images/SpringBoot项目快速启动.png)

我们将**SpringBoot项目最终编译的jar包**发送给前端开发人员，就可以在他的工作电脑上快速启动该项目，从而避免前后端联调时直连我们的电脑。

<span style="color:red;">这种方法需要一个前提：运行jar包的电脑上需要安装Java！</span>

在项目编译的jar包所在的目录下打开cmd命令行窗口，执行启动指令

```shell
$ java -jar springboot_01_quickstart.jar
```

<span style="color:red;">注意事项：</span>

jar包支持命令行快速启动是需要依赖maven插件支持的，请确认项目打包时是否具有SpringBoot对应的maven插件

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```



## 框架简介

SpringBoot框架的设计目的是用来<span style="color:red;">简化Spring应用的初始搭建以及开发过程</span>。

SpringBoot针对Spring框架的一些缺点进行了改良：

Spring

- 配置繁琐
- 依赖设置繁琐

SpringBoot

- 自动配置
- 起步依赖（简化依赖配置）
- 辅助功能（内置服务器，......）



**起步依赖**

在SpringBoot项目的`pom.xml`文件中

![起步依赖（启动器）](./images/起步依赖（启动器）.png)

像上图中这些依赖一般称为起步依赖，依赖名称中常带有 **starter** 单词，这些依赖定义了当前项目可能使用的所有依赖坐标，以达到<span style="color:red;">减少依赖配置</span>目的。

其中，**parent** 标签内是所有SpringBoot项目都要继承的父项目，定义了若干个坐标版本号（dependencyManagement 而非 dependencies），以达到<span style="color:red;">减少依赖冲突</span>目的。



**辅助功能**：在SpringBoot的起步依赖中还包含了一些辅助功能的依赖，例如内置的Tomcat服务器、JUnit、日志等。

对于这些辅助功能，我们可以手动将其排除或替换，例如我们可以将Tomcat服务器替换为Jetty服务器：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!--Jetty服务器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```



**SpringBoot程序启动**

SpringBoot程序通过引导类来启动，引导类是项目的入口，运行main方法就可以启动项目

```java
@SpringBootApplication
public class Springboot01QuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01QuickstartApplication.class, args);
    }

}
```

<span style="color:red;">SpringBoot项目采用jar的打包方式</span>



## 基础配置

SpringBoot提供了多种属性配置方式

- properties
- yml
- yaml



创建项目工程`springboot_02_base_config`，创建方式同上



https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=95