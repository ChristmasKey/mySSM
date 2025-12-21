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

并创建一个`BookController`

```java
package com.stone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println("id ==> " + id);
        return "Hello, Springboot!";
    }
}
```



然后我们通过多种配置文件格式来修改项目启动时的端口号

`application.properties`

```properties
server.port=8090
```

新建`application.yml`和`application.yaml`

```yaml
server:
  port: 8092
```

<b style="color:red;">其中，yml格式的配置文件是主流格式；三种格式的配置文件加载顺序为 properties > yml > yaml</b>



### yaml格式配置

![yaml格式1](./images/yaml格式1.png)

---

![yaml语法规则](./images/yaml语法规则.png)

---

![yaml数组数据](./images/yaml数组数据.png)



### yaml数据读取

创建项目工程`springboot_03_read_data`，创建方式同上

首先我们在yml配置文件中添加一些自定义数据

```yaml
# 自定义数据
lesson: "SpringBoot"
enterprise:
  name: "北京智谱华章科技有限公司"
  address: "北京市海淀区中关村南大街27号"
  phone: "010-12345678"
  email: "info@zhishupuhuazhang.com"
  website: "http://www.zhishupuhuazhang.com"
  employees:
    - name: "张三"
      age: 30
      position: "软件工程师"
    - name: "李四"
      age: 25
      position: "产品经理"
    - name: "王五"
      age: 35
      position: "项目经理"
```

**方式一**：使用**@Value**读取单个数据，属性名引用方式：${一级属性名.二级属性名...}

**方式二**：封装全部数据到**Environment**对象

**方式三**：自定义对象封装指定数据（使用**@ConfigurationProperties**注解）

`Enterprise`

```java
package com.stone.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 读取配置方式三：配置属性类
 */
@Component
@ConfigurationProperties(prefix = "enterprise") // 将配置文件中的属性绑定到 Java 对象上
public class Enterprise {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;

    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", employees=" + employees +
                '}';
    }
}
```

`Employee`

```java
package com.stone.domain;

public class Employee {

    private String name;
    private int age;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                '}';
    }
}
```

读取配置数据的三种方式实例

```java
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
```



### 多环境启动

![多环境启动](./images/多环境启动.png)

创建项目工程`springboot_04_profile`，创建方式同上

**yml格式**：配置多环境

```yaml
# 设置启用的环境
spring:
  profiles:
    active: test

---
# 开发环境
spring:
  application:
    name: springboot_04_profile
  config:
    activate:
      on-profile: dev
server:
  port: 8080

---
# 测试环境
spring:
  application:
    name: springboot_04_profile
  config:
    activate:
      on-profile: test
server:
  port: 8081

---
# 生产环境
spring:
  application:
    name: springboot_04_profile
  config:
    activate:
      on-profile: prod
server:
  port: 8082
```

**properties格式**：配置多环境

application-prod.properties

```properties
server.port=8090
```

application.properties

```properties
# 设置启用的环境
spring.profiles.active=prod
```



#### 命令行启动参数

```shell
$ java -jar xxx.jar --spring.profiles.active=prod --server.port=9090
```



#### 多环境开发控制

创建项目工程`springboot_05_maven_and_boot_profile`，创建方式同上

配置多环境，并在`pom.xml`中添加如下配置

```xml
<build>
    <plugins>
        <!--
            Maven资源插件，主要用于处理项目资源文件
            它负责将src/main/resources目录下的资源文件复制到输出目录（通常是target/classes）
            -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.2.0</version>
            <configuration>
                <!--指定资源文件的编码格式为UTF-8，确保在处理包含非ASCII字符的资源文件时不会出现乱码-->
                <encoding>utf-8</encoding>
                <!--启用默认的分隔符，这允许在资源文件中使用变量占位符（如${variable.name}），Maven会在构建时替换这些占位符为实际值-->
                <useDefaultDelimiters>true</useDefaultDelimiters>
            </configuration>
        </plugin>
    </plugins>
</build>

<!--配置多环境-->
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <profile.active>dev</profile.active>
        </properties>
    </profile>
    <profile>
        <id>test</id>
        <properties>
            <profile.active>test</profile.active>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <profile.active>prod</profile.active>
        </properties>
        <!--将该环境设置为启动环境-->
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
</profiles>
```

随后对`application.yml`稍加修改

```yaml
spring:
  profiles:
    # 读取Maven中指定的运行环境
    active: ${profile.active}
```

此后，每次变更运行环境只需要将`pom.xml`中对应的环境激活即可



### 配置文件分类

![SpringBoot配置文件分类](./images/SpringBoot配置文件分类.png)

新建项目工程`springboot_06_config_file`，创建方式同上

分别下面四个路径下添加配置文件`application.yml`

`resource`：对应4级

`resource\config`：对应3级

`jar包所在目录`：对应2级

`jar包所在目录\config`：对应1级

<span style="color:red;">可以发现这四级配置文件会依次覆盖</span>



## 整合

### JUnit

![Spring整合JUnit复习](./images/Spring整合JUnit复习.png)



### SSM



#### MybatisPlus

https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=101

