# myMaven笔记

## Maven简介

传统项目管理状态分析

- jar包不统一，jar包不兼容
- 工程升级维护过程操作繁琐
- ……

<span style="color:red;">Maven的本质是一个项目管理工具，将项目开发和管理过程抽象成一个项目对象模型（POM）</span>

POM：Project Object Model 项目对象模型

（<b style="color:blue;">关于pom.xml文件的内容，可以查看《POM文件帮助文档.md》</b>）

![Maven简介](./images/Maven简介.png)

**Maven的作用**

- 项目构建：提供标准的、跨平台的自动化项目构建方式
- 依赖管理：方便快捷的管理项目依赖的资源（jar包），避免资源间的版本冲突问题
- 统一开发结构：提供标准的、统一的项目结构

![Maven统一的项目结构](./images/Maven统一的项目结构.png)



## Maven的下载&安装

官网：https://maven.apache.org/

下载地址：https://maven.apache.org/download.cgi

![Maven下载页面](./images/Maven下载页面.png)

将下载的压缩包解压后，可以看到如下目录结构

![Maven目录结构](./images/Maven目录结构.png)

配置环境变量

JAVA_HOME配置：略

MAVEN_HOME配置：

1、新建**MAVEN_HOME**系统变量

![MAVEN_HOME系统变量](./images/MAVEN_HOME系统变量.png)

2、修改系统变量**Path**

![修改系统变量Path](./images/修改系统变量Path.png)

3、通过CMD命令验证Maven是否安装成功

![验证Maven安装成功](./images/验证Maven安装成功.png)



## 基础概念

**仓库**

- 仓库：用于存储资源，包含各种 jar 包
- 仓库分类:
    - 本地仓库：自己电脑上存储资源的仓库，连接远程仓库获取资源
    - 远程仓库：非本机电脑上的仓库，为本地仓库提供资源
        - 中央仓库: Maven 团队维护，存储所有资源的仓库
        - 私服：部门 / 公司范围内存储资源的仓库，从中央仓库获取资源
- 私服的作用:
    - 保存具有版权的资源，包含购买或自主研发的 jar
        - 中央仓库中的 jar 都是开源的，不能存储具有版权的资源
    - 一定范围内共享资源，仅对内部开放，不对外共享

<img src="./images/Maven仓库概念.png" alt="Maven仓库概念" style="zoom:50%;" />

**坐标**

- 什么是坐标？

    - Maven 中的坐标用于描述仓库中资源的位置
    - https://repo1.maven.org/maven2/

- Maven 坐标主要组成

    - groupId：定义当前 Maven 项目隶属组织名称（通常是域名反写，例如：org.mybatis）

    - artifactId：定义当前 Maven 项目名称（通常是模块名称，例如 CRM、SMS）

    - version：定义当前项目版本号

    - packaging：定义该项目的打包方式

- Maven 坐标的作用
    - <span style="color:red;">使用唯一标识，唯一性定位资源位置，通过该标识可以将资源的识别与下载工作交由机器完成</span>



## 仓库配置

**配置本地仓库**

![Maven配置本地仓库](./images/Maven配置本地仓库.png)

**配置远程仓库（镜像仓库配置）**

![Maven配置远程仓库](./images/Maven配置远程仓库.png)



## 首个Maven项目

### 手动生成

手动创建Maven工程目录结构

> project
>
> - src
>     - main
>         - java
>             - com
>                 - stone
>                     - HelloWorld.java
>         - resources
>     - test
>         - java
>             - com
>                 - stone
>                     - HelloWorldTest.java
>         - resources
> - pom.xml

`HelloWorld`

```java
package com.stone;

public class HelloWorld {
	public String hello(String name) {
		System.out.println("Hello " + name);
		return "Hello " + name;
	}
}
```

`HelloWorldTest`

```java
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
```

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <!--Maven对象模型版本号-->
	<modelVersion>4.0.0</modelVersion>

    <groupId>com.stone</groupId>
    <artifactId>project-java</artifactId>
    <version>1.0</version>

    <packaging>jar</packaging>

    <name>project-java</name>

    <dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
		</dependency>
    </dependencies>
</project>
```

使用Maven命令来构建项目

<span style="color:red;">Maven命令使用mvn开头，后面添加功能参数，可以一次执行多个命令，使用空格分隔</span>

```shell
$ mvn compile	# 编译
$ mvn clean		# 清理
$ mvn test		# 测试
$ mvn package	# 打包
$ mvn install	# 安装到本地仓库
```

在项目的根路径下，使用cmd窗口来运行命令



此外，我们还可以通过插件来创建项目工程

- 创建工程

```shell
$ mvn archetype:generate
$    -DgroupId={project-packaging}
$    -DartifactId={project-name}
$    -DarchetypeArtifactId=maven-archetype-quickstart
$    -DinteractiveMode=false
```

- 创建java工程

```shell
$ mvn archetype:generate
$    -DgroupId=com.stone
$    -DartifactId=java-project
$    -DarchetypeArtifactId=maven-archetype-quickstart
$    -Dversion=0.0.1-snapshot 
$    -DinteractiveMode=false
```

- 创建Web工程

```shell
$ mvn archetype:generate
$    -DgroupId=com.itheima
$    -DartifactId=web-project
$    -DarchetypeArtifactId=maven-archetype-webapp
$    -Dversion=0.0.1-snapshot
$    -DinteractiveMode=false
```



### IDEA生成

要想通过IDEA生成Maven项目，<span style="color:red;">首先需要给IDEA配置Maven</span>，

我们创建一个空项目工程

![创建一个空项目工程](./images/创建一个空项目工程.png)

修改`Project Structure`中的Project SDK

![修改Project SDK](./images/修改Project SDK.png)

然后在IDEA中配置Maven

![在IDEA中配置Maven](./images/在IDEA中配置Maven.png)

#### 手工创建Java项目

![手动创建Java项目](./images/手动创建Java项目.png)

引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.stone</groupId>
    <artifactId>java01</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

编写类和方法

![java01项目结构](./images/java01项目结构.png)

`HelloWorld`

```java
package com.stone;

public class HelloWorld {

    public String sayHello(String name) {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }
}
```

`HelloWorldTest`

```java
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
```

通过Maven命令去构建项目

![IDEA中的Maven构建命令](./images/IDEA中的Maven构建命令.png)

当我们点击Test命令时，Maven会扫描并运行`test`目录下的测试类中的方法

![Maven的Test命令执行结果](./images/Maven的Test命令执行结果.png)

此外我们还可以自定义配置Maven运行环境（<span style="color:red;">方便我们进行Debug调试</span>）

![自定义Maven运行环境](./images/自定义Maven运行环境.png)

#### 原型创建Java项目

![使用Maven原型创建java项目工程](./images/使用Maven原型创建java项目工程.png)

项目工程结构如下

![java02项目工程结构](./images/java02项目工程结构.png)

#### 原型创建Web项目

![使用Maven原型创建Web项目工程](./images/使用Maven原型创建Web项目工程.png)

项目工程结构如下，需要我们手动添加`java`目录

![web01项目工程结构](./images/web01项目工程结构.png)

#### 插件

对于Web工程，我们需要安装Tomcat插件，才能启动它

在pom.xml中添加插件配置

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <!--指定pom的模型版本-->
    <modelVersion>4.0.0</modelVersion>

    <!--项目坐标-->
    <groupId>com.stone</groupId>
    <artifactId>web01</artifactId>
    <!--版本号，SNAPSHOT为快照版本，RELEASE为正式版本-->
    <version>1.0-SNAPSHOT</version>

    <!--打包方式，web工程打war包，java工程打jar包-->
    <packaging>war</packaging>

    <name>web01</name>

    <!--当前项目工程引入的所有依赖-->
    <dependencies>
    </dependencies>

    <!--构建-->
    <build>
        <!--插件配置-->
        <plugins>
            <!--Tomcat插件-->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <!--Tomcat配置-->
                <configuration>
                    <!--路径-->
                    <path>/</path>
                    <!--端口-->
                    <port>8090</port>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

通过插件命令启动项目

![Tomcat插件命令](./images/Tomcat插件命令.png)

访问地址：http://localhost:8090/

![web01项目启动结果](./images/web01项目启动结果.png)

当然，我们也可以在自定义配置运行环境中配置插件的命令

![自定义运行环境配置插件命令](./images/自定义运行环境配置插件命令.png)



## 依赖管理

**依赖配置**：配置当前项目工程运行所需的jar，一个项目工程可以配置多个依赖

格式如下：

```xml
<!--当前项目工程引入的所有依赖-->
<dependencies>
    <!--每一个具体的依赖-->
    <dependency>
        <!--依赖的群组ID-->
        <groupId>junit</groupId>
        <!--依赖的项目ID-->
        <artifactId>junit</artifactId>
        <!--依赖的版本号-->
        <version>4.12</version>
    </dependency>
</dependencies>
```



**依赖传递**

依赖具有传递性

- 直接依赖：在当前项目工程中通过依赖配置建立的依赖关系
- 简介依赖：被配置的依赖资源如果依赖其他的资源，当前项目间接依赖其他资源

<span style="color:red;">依赖传递冲突问题</span>

- 路径优先：当依赖中出现相同的资源时，层级越深，优先级越低，层级越浅，优先级越高
- 声明优先：当资源在相同层级被依赖时，配置顺序靠前的覆盖配置顺序靠后的
- 特殊优先：当同级配置了相同资源的不同版本，后配置的覆盖先配置的

![project01依赖传递](./images/project01依赖传递.png)

我们在`project01`中依赖`junit`后，`junit`依赖的资源`hamcrest`也可以被间接使用到



**可选依赖**：指对外隐藏当前所依赖的资源——不透明

<span style="color:red;">在目标依赖中配置`optional`选项为 true</span>

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <optional>true</optional>
</dependency>
```

当我们在`project03`中将`junit`设置为可选依赖后，在`project02`中引入`project03`时是看不到`junit`依赖的，这就是可选依赖的<span style="color:red;">不透明性</span>

![project03可选依赖](./images/project03可选依赖.png)



**排除依赖**：指主动断开依赖的资源，被排除的资源无需指定版本——不需要

<span style="color:red;">在引入目标依赖的地方配置`exclusions`选项</span>

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <exclusions>
        <exclusion>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

![project02排除依赖](./images/project02排除依赖.png)



**依赖范围**：依赖的jar默认情况可以在任何地方使用，可以通过`scope`标签设定其作用范围

作用范围

- 主程序范围有效（main文件夹范围内）
- 测试程序范围有效（test文件夹范围内）
- 是否参与打包（package指令范围内）

![常见scope值与对应范围](./images/常见scope值与对应范围.png)

依赖范围的传递性

![依赖范围的传递性](./images/依赖范围的传递性.png)



## 生命周期与插件

### 生命周期

Maven的生命周期描述的是一次构建过程经历了多少个事件

![Maven生命周期](./images/Maven生命周期.png)

Maven对项目构建的生命周期划分为3套方案

- **clean**：清理工作

![clean生命周期](./images/clean生命周期.png)

- **default**：核心工作，如编译、测试、打包、部署等

![default生命周期](./images/default生命周期.png)

- **site**：产生报告，发布站点等

![site生命周期](./images/site生命周期.png)

### 插件

插件与生命周期内的阶段绑定，在执行到对应生命周期时执行对应的插件功能

<span style="color:red;">默认Maven在各个生命周期上绑定有预设的功能</span>

通过插件可以自定义其他功能，语法格式如下：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>2.2.1</version>
            <executions>
                <execution>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                    <phase>generate-test-resources</phase>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

插件可以在Maven官网的`Maven Plugins`菜单页面查看&复制依赖



## Maven高级

### 分模块开发与设计

<span style="color:blue;">一个项目工程的目录结构可以被划分成多个相互独立的子模块（子工程）</span>

![工程模块与模块划分](./images/工程模块与模块划分.png)

<span style="color:red;">接下来，我们就尝试以模块划分的形式来创建一个SSM整合项目</span>

#### POJO拆分

首先创建一个空项目`ssm_multi_modules`

![ssm_multi_modules](./images/ssm_multi_modules.png)

然后创建一个Maven模块`ssm_pojo`

![ssm_pojo](./images/ssm_pojo.png)

删改后项目结构如下：

![ssm_pojo项目结构](./images/ssm_pojo项目结构.png)

在其中创建`User`类

```java
package com.stone.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer uuid;
    private String userName;
    private String password;
    private String realName;
    private Integer gender;
    private Date birthday;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                '}';
    }
}
```

#### DAO拆分

创建一个Maven模块`ssm_dao`

https://www.bilibili.com/video/BV1Ah411S7ZE?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=16