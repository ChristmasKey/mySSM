# mySSM笔记

![首图](./images/首图.avif)

## Spring

官网：https://spring.io/

**Spring Framework系统架构**

![Spring Framework系统架构](./images/SpringFramework系统架构.png)

**Spring Framework学习线路**

![SpringFramework学习线路](./images/SpringFramework学习线路.png)



### 核心概念

代码书写现状：<span style="color:blue;">由于在类中写了其他类的实现，导致代码耦合度偏高，每次改动代码都需要重新编译、测试、部署和发布</span>

![代码书写现状](./images/代码书写现状.png)



解决方案：（<span style="color:red;">解耦合</span>）使用对象时，在程序中不要主动使用new产生对象，转为由<span style="color:red;">外部</span>提供对象

---

<b style="color:red;">IoC（Inversion of Control）控制反转</b>

- 使用对象时，由主动new产生对象转换为由<span style="color:red;">外部</span>提供对象，此过程中对象创建控制权由程序转移到<span style="color:red;">外部</span>，此思想称为控制反转

**Spring技术对IoC思想进行了实现**

- Spring提供了一个容器，称为<b style="color:red;">IoC容器</b>，用来充当IoC思想中的“<span style="color:red;">外部</span>”（对应Spring Framework系统架构中的**Core Container**）
- IoC容器负责对象的创建、初始化等一系列工作，被创建或被管理的对象在IoC容器中统称为<b style="color:red;">Bean</b>

<b style="color:red;">DI（Dependency Injection）依赖注入</b>

- 在容器中建立Bean与Bean之间的依赖关系的整个过程，称为依赖注入

---

<span style="color:blue;">将对象交由IoC容器管理，需要使用时由IoC容器代为创建对象实例</span>

<span style="color:blue;">除了dao层对象，service层对象同样可以交给IoC容器进行管理，此时IoC容器中就管理着大量的对象（IoC容器管理着它们的创建和初始化的过程）</span>

<span style="color:blue;">由于service层对象依赖dao层对象运行，并且这两种对象都交给了IoC容器管理，所以可以更进一步的将二者的依赖关系也交给IoC容器来维护</span>

<span style="color:red;">最终实现效果：当我们需要使用service层对象时，IoC容器会为我们创建并初始化好对象实例，同时其依赖的dao层对象也会被一并创建好，并绑定好依赖关系</span>

![SpringFramework核心概念讲解案例](./images/SpringFramework核心概念讲解案例.png)

---

**总结**

最终目标：<span style="color:red;">充分解耦</span>

- 使用IoC容器管理Bean（IoC）
- 在IoC容器内将所有依赖关系的Bean进行关系绑定（DI）

最终效果：<span style="color:red;">使用对象时不仅可以直接从Ioc容器中获取，并且获取到的Bean已经绑定了所有的依赖关系</span>



### 入门案例

#### 1、IoC入门案例

<b style="color:red;">案例思路分析：</b>

- 管理什么：Service与Dao
- 如何将被管理的对象告知IoC容器：配置
- 被管理的对象交给IoC容器，如何获取到IoC容器：接口
- IoC容器得到后，如何从中获取Bean：（接口方法）
- 使用Spring导入哪些（依赖）坐标：pom.xml



##### ①创建项目

![IoC入门案例-创建项目工程](./images/IoC入门案例-创建项目工程.png)



##### ②引入依赖坐标

主要导入`spring-context`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.stone</groupId>
    <artifactId>ioc_demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>ioc_demo</name>
    <dependencies>
        <!--spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.27</version>
        </dependency>
        <!--junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```



##### ③创建配置文件

<span style="color:red;">需要先创建 `resources` 目录</span>

![IoC入门案例-创建Spring配置文件](./images/IoC入门案例-创建Spring配置文件.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置Bean-->
    <!--
    bean标签表示配置Bean
    id属性表示Bean在IoC容器中的唯一标识
    class属性指定Bean的类型
    -->
    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl"/>
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
</beans>
```



##### ④获取IoC容器和Bean

任意创建一个类用来获取IoC容器和Bean，其中的main方法如下：

```java
public static void main(String[] args) {
    // 获取IoC容器
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    // 从容器中获取Bean对象
    //BookDao bookDao = (BookDao) context.getBean("bookDao");
    //bookDao.save();
    BookService bookService = (BookService) context.getBean("bookService");
    bookService.save();
}
```



##### ⑤Service层&Dao层代码

`BookService`

```java
package com.stone.service;

public interface BookService {

    void save();
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.dao.impl.BookDaoImpl;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```



##### ⑥执行main方法，查看运行结果

![IoC入门案例执行结果](./images/IoC入门案例执行结果.png)



#### 2、DI入门案例

<b style="color:red;">案例思路分析：</b>

- 基于IoC管理Bean
- Service中不再使用new形式创建Dao对象
- Service中需要的Dao对象如何创建获取：提供方法
- Service与Dao间的关系如何描述：配置



##### ①改造Service层代码

删除Service中使用new的方式创建Dao对象的代码；提供依赖的Dao对象对应的setter方法。

`BookServiceImpl.java`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    // 移除new方式创建的对象
    private BookDao bookDao;

    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }

    // 提供dao对象对应的setter方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
```



##### ②配置service与dao之间的关系

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置Bean-->
    <!--
    bean标签表示配置Bean
    id属性表示Bean在IoC容器中的唯一标识
    class属性指定Bean的类型
    -->
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <!--配置Service与Dao的关系-->
        <!--
        property标签表示配置当前Bean的属性
        name属性表示配置Bean中哪一个具体的属性
        ref属性表示参照哪一个bean标签
        -->
        <property name="bookDao" ref="bookDao"/>
    </bean>
</beans>
```

property标签中两种属性的含义：

![property标签中属性的含义](./images/property标签中属性的含义.png)



##### ③重新执行main方法

![IoC入门案例执行结果](./images/IoC入门案例执行结果.png)



#### 3、Bean的配置

首先创建一个新的项目工程，代码内容与入门案例工程一样，后续配置的修改基于这个新项目

![Bean基础配置-创建项目工程](./images/Bean基础配置-创建项目工程.png)

**Bean基础配置**

![Bean基础配置](./images/Bean基础配置.png)



**Bean别名配置**：为Bean设置多个别名

![Bean别名配置](./images/Bean别名配置.png)

在配置文件中为Bean设置别名

```xml
<bean id="bookService" name="bookService2 bookService3 bookService4 "
          class="com.stone.service.impl.BookServiceImpl" />
```

修改`main`方法中获取Bean的方法

```java
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    BookService bookService = (BookService) context.getBean("bookService2");
    bookService.save();
}
```

当我们通过指定一个从未定义过的别名来获取Bean时，会报错：<b style="color:red;">NoSuchBeanDefinitionException</b>

```java
BookService bookService = (BookService) context.getBean("myBookService2");
```

![通过未定义的别名获取Bean时报错](./images/通过未定义的别名获取Bean时报错.png)



**Bean作用范围配置**：通过Spring创建的Bean对象是单例的还是非单例的

![Bean作用范围配置](./images/Bean作用范围配置.png)

通过IoC容器创建两个相同的Bean对象，并打印查看它们的内存地址，会发现它们的内存地址是一样的，这就意味着Spring为我们创建的Bean对象默认是单例的

```java
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    BookDao bookDao1 = (BookDao) context.getBean("bookDao");
    BookDao bookDao2 = (BookDao) context.getBean("bookDao");
    System.out.println(bookDao1);
    System.out.println(bookDao2);
}
```

![Spring默认创建两个相同的Bean对象](./images/Spring默认创建两个相同的Bean对象.png)

<span style="color:red;">通过配置，我们可以让Spring创建两个非单例的Bean对象</span>

```xml
<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl" scope="prototype"/>
```

![Spring创建两个非单例的Bean对象](./images/Spring创建两个非单例的Bean对象.png)

<b style="color:blue;">为什么Spring中的Bean默认为单例的？</b>

对于Spring来说，它帮我们管理的Bean需要放到IoC容器中，如果创建的Bean对象非单例的，那么在每次使用时都会创建一个Bean对象，这会导致IoC容器中的Bean对象越来越多，造成不必要的资源浪费。<span style="color:red;">由此可见，并非所有对象都适合交给Spring作为Bean去管理！</span>

- 适合交给容器进行管理的Bean：可以重复使用的对象
    - 表现层对象
    - 业务层对象
    - 数据层对象
    - 工具对象
- 不适合交给容器进行管理的对象：有状态的对象（如domain对象，其中记录着属性的状态值）
    - 封装实体的域对象



#### 4、Bean的实例化

创建一个新的项目工程`bean_instance`

![Bean实例化-创建项目工程](./images/Bean实例化-创建项目工程.png)

<span style="color:red;">Bean是如何创建的：Bean本质上就是对象，Spring默认调用构造方法来创建Bean对象。</span>

我们在项目中创建一个Dao对象并把它交给Spring去管理：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    /**
     * Spring是通过构造方法来创建Bean对象的
     * 并且无论构造方法是public还是private，Spring都可以执行
     * 可见Spring底层是通过反射来获取构造方法的！！！
     */
    public BookDaoImpl() {
        System.out.println("book dao constructor is running ...");
    }

    public void save() {
        System.out.println("book dao save ...");
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--方式一：通过构造方法实例化Bean-->
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
</beans>
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForInstanceBook {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookDao.save();
    }
}
```

执行main方法后查看控制台打印结果

![Spring通过构造器创建Bean对象](./images/Spring通过构造器创建Bean对象.png)

<span style="color:red;">如果我们将构造方法稍作改动，Spring还能调用它吗？</span>

```java
public BookDaoImpl(int i) {
    System.out.println("book dao constructor is running ...");
}
```

会发现报错了：

![Spring调用有参构造函数报错](./images/Spring调用有参构造函数报错.png)

<b style="color:blue;">由此可知：Spring只能调用无参构造方法来创建Bean对象！</b>



##### 实例化Bean的三种方式

**①构造方法（常用）**

- 提供可访问的构造方法（无参构造方法默认提供，可以缺省）

```java
public class BookDaoImpl implements BookDao {

    //public BookDaoImpl() {
    //    System.out.println("book dao constructor is running ...");
    //}

    public void save() {
        System.out.println("book dao save ...");
    }
}
```

- 配置Bean

```java
<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
```



**②静态工厂（了解）**

新建一个Dao对象

`OrderDao`

```java
package com.stone.dao;

public interface OrderDao {

    void save();
}
```

`OrderDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.OrderDao;

public class OrderDaoImpl implements OrderDao {

    public void save() {
        System.out.println("order dao save ...");
    }
}
```

<span style="color:red;">我们再创建一个静态工厂类，用来创建OrderDao对象实例</span>

```java
package com.stone.factory;

import com.stone.dao.OrderDao;
import com.stone.dao.impl.OrderDaoImpl;

public class OrderDaoFactory {

    public static OrderDao getOrderDaoInstance() {
        return new OrderDaoImpl();
    }
}
```

于是我们可以在main方法中这样编写：

```java
public static void main(String[] args) {
    // 通过静态工厂创建对象
    OrderDao orderDao = OrderDaoFactory.getOrderDaoInstance();
    orderDao.save();
}
```

那么如何在Spring中使用静态工厂实例化Bean呢？<span style="color:red;">我们可以在配置文件中做如下配置</span>：

```xml
<!--方式二：通过静态工厂实例化Bean-->
<bean id="orderDao" class="com.stone.factory.OrderDaoFactory" factory-method="getOrderDaoInstance"/>
```

修改main方法：

```java
public static void main(String[] args) {
    // 通过静态工厂创建对象
    //OrderDao orderDao = OrderDaoFactory.getOrderDaoInstance();
    //orderDao.save();

    // 通过Spring调用静态工厂创建对象
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    OrderDao orderDao = (OrderDao) context.getBean("orderDao");
    orderDao.save();
}
```

最终控制台的打印结果如下：

![Spring通过静态工厂创建Bean对象](./images/Spring通过静态工厂创建Bean对象.png)



**③实例工厂与FactoryBean（了解）**

同样创建一个Dao对象

`UserDao`

```java
package com.stone.dao;

public interface UserDao {

    void save();
}
```

`UserDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.UserDao;

public class UserDaoImpl implements UserDao {

    public void save() {
        System.out.println("user dao save ...");
    }
}
```

<span style="color:red;">我们再创建一个实例工厂类，用来创建UserDao对象实例（实例工厂与静态工厂的区别在于它的方法非静态的）</span>

```java
package com.stone.factory;

import com.stone.dao.UserDao;
import com.stone.dao.impl.UserDaoImpl;

public class UserDaoFactory {

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
```

在main方法中编写代码如下：

```java
public static void main(String[] args) {
    // 创建实例工厂对象
    UserDaoFactory userDaoFactory = new UserDaoFactory();
    // 通过实力工厂对象创建Dao对象实例
    UserDao userDao = userDaoFactory.getUserDao();
    userDao.save();
}
```

要在Spring中使用实例工厂来实例化Bean，<span style="color:red;">我们可以在配置文件中做如下配置</span>：

```xml
<!--方式三：通过实例工厂实例化Bean-->
<!--首先 创建实例工厂Bean-->
<bean id="userDaoFactory" class="com.stone.factory.UserDaoFactory"/>
<!--其次 指定实例工厂Bean来创建Dao对象Bean-->
<bean id="userDao" factory-bean="userDaoFactory" factory-method="getUserDao"/>
```

修改main方法：

```java
public static void main(String[] args) {
    // 创建实例工厂对象
    //UserDaoFactory userDaoFactory = new UserDaoFactory();
    // 通过实力工厂对象创建Dao对象实例
    //UserDao userDao = userDaoFactory.getUserDao();
    //userDao.save();

    // 通过Spring创建实例工厂对象
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDao userDao = (UserDao) context.getBean("userDao");
    userDao.save();
}
```

最终控制台打印结果如下：

![Spring通过实例工厂创建Bean对象](./images/Spring通过实例工厂创建Bean对象.png)



<b style="color:red;">对于第三种方式，需要创建两种Bean，显然有点鸡肋，因此Spring专门针对其进行了改良（重要）</b>

首先我们需要创建一个 `UserDaoFactoryBean` 并实现 `FactoryBean` 接口及其中的方法

```java
package com.stone.factory;

import com.stone.dao.UserDao;
import com.stone.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.FactoryBean;

public class UserDaoFactoryBean implements FactoryBean<UserDao> {

    // 代替原始实例工厂中创建对象的方法
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }

    // 获取Bean对象类型
    public Class<?> getObjectType() {
        return UserDao.class;
    }
}
```

其次我们只需要在配置文件中配置一个上述的 `FactoryBean` 即可

```xml
<!--方式四：通过FactoryBean实例化Bean-->
<bean id="userDao2" class="com.stone.factory.UserDaoFactoryBean"/>
```

其他代码不需要改动，再次运行main方法，控制台依旧可以打印出同上的结果（图略）



**此时我们需要思考，通过这种改良方式创建出来的Bean对象实例是否是单例的**

可以在main方法中验证一下

```java
public static void main(String[] args) {
    // 创建实例工厂对象
    //UserDaoFactory userDaoFactory = new UserDaoFactory();
    // 通过实力工厂对象创建Dao对象实例
    //UserDao userDao = userDaoFactory.getUserDao();
    //userDao.save();

    // 通过Spring创建实例工厂对象
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDao userDao = (UserDao) context.getBean("userDao2");
    userDao.save();

    UserDao userDao2 = (UserDao) context.getBean("userDao2");
    System.out.println(userDao);
    System.out.println(userDao2);

}
```

从控制台打印结果来看，Bean对象任然是单例的

![Spring基于实例工厂改良的方式创建单例Bean对象实例](./images/Spring基于实例工厂改良的方式创建单例Bean对象实例.png)



通过实现 `FactoryBean` 中的 *isSingleton* 方法，可以配置创建的Bean对象是否为单例的

```java
// 配置Bean对象是否为单例
public boolean isSingleton() {
    return false;
}
```

重新执行main方法，查看控制台打印

![Spring基于实例工厂改良的方式创建非单例Bean对象实例](./images/Spring基于实例工厂改良的方式创建非单例Bean对象实例.png)



#### 5、Bean的生命周期

生命周期：从创建到消亡的完整过程

Bean生命周期：Bean从创建到销毁的整体过程

Bean生命周期控制：在Bean创建后到销毁前做一些事情



创建一个新的项目工程`bean_lifecycle`

![Bean生命周期-创建项目工程](./images/Bean生命周期-创建项目工程.png)

项目基本代码内容如下：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    public void save() {
        System.out.println("book dao save ...");
    }
}
```

`BookService`

```java
package com.stone.service;

public interface BookService {

    void save();
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        bookDao.save();
        System.out.println("book service save ...");
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置Bean-->
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
</beans>
```

main方法：只引入了Dao对象，并未引入Service对象

```java
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    BookDao bookDao = (BookDao) context.getBean("bookDao");
    bookDao.save();
}
```



##### 配置形式控制生命周期

我们尝试一下控制Dao对象的生命周期，在`BookDaoImpl`中新建两个方法

```java
// 表示Bean初始化对应的操作
public void init() {
    System.out.println("init...");
}

// 表示Bean销毁前对应的操作
public void destroy() {
    System.out.println("destroy...");
}
```

并将这两个方法配置到`applicationContext.xml`中

```xml
<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl" init-method="init" destroy-method="destroy"/>
```

执行main方法后，结果如下

![Bean的生命周期方法运行结果](./images/Bean的生命周期方法运行结果.png)

显然，*destroy()* 方法并未被执行，这是因为在上面的代码中，程序执行完成后，JVM虚拟机会直接退出，并没有给Bean留出销毁的时间。

因此，如果想让 *destroy()* 方法执行，我们可以在虚拟机退出之前将IoC容器关闭：

修改main方法中的代码，将 `ApplicationContext` 类型替换为 `ClassPathXmlApplicationContext` 类型，并调用其 *close()* 方法

```java
public static void main(String[] args) {
    // 转换IoC容器的接收类型
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    BookDao bookDao = (BookDao) context.getBean("bookDao");
    bookDao.save();
    // 手动关闭IoC容器
    context.close();
}
```

重新执行main方法后，结果如下

![Bean的生命周期方法运行结果1](./images/Bean的生命周期方法运行结果1.png)

上面的关闭容器操作是一种**暴力关闭**的方式，我们还有一种更优雅的方式来关闭IoC容器：在main方法中<i style="color:red;">设置关闭钩子</i>

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForLifeCycle {

    public static void main(String[] args) {
        // 转换IoC容器的接收类型
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookDao.save();
        // 注册关闭钩子
        context.registerShutdownHook(); // 在创建context后，随时都可以调用
        // 手动关闭IoC容器
        //context.close(); // 只能在代码执行的最后调用
    }
}
```



##### 接口形式控制生命周期

为了简化配置，Spring还提供了接口的形式来控制Bean的生命周期。接下来，我们尝试一下用这种形式来控制Service对象的生命周期。

在`BookServiceImpl`中实现两个接口 `InitializingBean` 和 `DisposableBean` 并分别实现其中的方法 **destroy()** 和 **afterPropertiesSet()**：

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        bookDao.save();
        System.out.println("book service save ...");
    }

    public void destroy() throws Exception {
        System.out.println("service destroy ...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("service init ...");
    }
}
```

main方法中的内容保持不变，重新执行，结果如下

![Bean的生命周期方法运行结果2](./images/Bean的生命周期方法运行结果2.png)

<span style="color:red;">注意：虽然Service对象并没有在main方法中被调用，但是会被加载到IoC容器中，所以它的生命周期方法也会执行</span>



<span style="color:red;">关于方法 **afterPropertiesSet()** 的细节</span>：从名字上就可以看出，它会在Bean对象中的属性全部设置好之后再运行。

修改 `BookServiceImpl` 类中的 Dao对象setter方法

```java
public void setBookDao(BookDao bookDao) {
    System.out.println("setBookDao...");
    this.bookDao = bookDao;
}
```

重新执行main方法后，结果如下

![Bean的生命周期方法运行结果3](./images/Bean的生命周期方法运行结果3.png)



##### 生命周期中各阶段操作

- 初始化容器

    1.创建对象（内存分配）

    2.执行构造方法

    3.执行属性输入（set操作）

    <span style="color:blue;">4.执行Bean初始化方法</span>

- 使用Bean

    1.执行业务操作

- 关闭/销毁容器

    <span style="color:blue;">1.执行Bean销毁方法</span>



#### 6、依赖注入方式

思考：向一个类中传递数据的方式有几种？

- 普通方法（setter）
- 构造方法

思考：依赖注入描述了在容器中建立Bean与Bean之间依赖关系的过程，如果Bean运行需要的是数字或字符串呢？

将依赖注入的数据按类型进行分类：

- 引用类型
- 简单类型（包括基本类型和String）



<span style="color:red;">综上，依赖注入的方式有以下几种：</span>

- setter注入
    - 简单类型
    - 引用类型（之前使用的都是这种方法）
- 构造器注入
    - 简单类型
    - 引用类型



##### setter注入

创建一个新的项目工程`di_set`

![依赖注入set方式-创建项目工程](./images/依赖注入set方式-创建项目工程.png)

项目基本代码内容如下：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`BookService`

```java
package com.stone.service;

public interface BookService {

    void save();
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.dao.UserDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
</beans>
```

main方法：

```java
package com.stone;

import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDISet
{
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = (BookService) context.getBean("bookService");
        bookService.save();
    }
}
```



**setter注入引用类型**：向`BookService`中注入`UserDao`

`UserDao`

```java
package com.stone.dao;

public interface UserDao {

    void save();
}
```

`UserDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public void save() {
        System.out.println("user dao save...");
    }
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.dao.UserDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private UserDao userDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
        userDao.save();
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userDao" class="com.stone.dao.impl.UserDaoImpl"/>
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
        <property name="userDao" ref="userDao"/>
    </bean>
</beans>
```

最终运行结果如下：

![setter注入引用类型运行结果](./images/setter注入引用类型运行结果.png)



**setter注入简单类型**：向`BookDao`中注入简单类型

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    private int connectionNum;

    private String databaseName;

    public void setConnectionNum(int connectionNum) {
        this.connectionNum = connectionNum;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void save() {
        System.out.println("book dao save..." + connectionNum + "; " + databaseName);
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userDao" class="com.stone.dao.impl.UserDaoImpl"/>
    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
        <!--使用value属性向Bean中注入简单类型依赖-->
        <property name="connectionNum" value="10"/>
        <property name="databaseName" value="mysql"/>
    </bean>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
        <property name="userDao" ref="userDao"/>
    </bean>
</beans>
```

最终运行结果如下：

![setter注入简单类型运行结果](./images/setter注入简单类型运行结果.png)



##### 构造器注入

创建一个新的项目工程`di_constructor`

![依赖注入构造器方式-创建项目工程](./images/依赖注入构造器方式-创建项目工程.png)

项目基本代码内容如下：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    private int connectionNum;

    private String databaseName;

    @Override
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`UserDao`

```java
package com.stone.dao;

public interface UserDao {

    void save();
}
```

`UserDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public void save() {
        System.out.println("user dao save...");
    }
}
```

`BookService`

```java
package com.stone.service;

public interface BookService {

    void save();
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao"/>
    </bean>
</beans>
```

main方法

```java
package com.stone;

import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForConstructor {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.save();
    }
}
```



**使用构造器注入方法注入两种类型的依赖**：

1、修改`BookServiceImpl`类，将set方法替换为带参构造方法

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    //public void setBookDao(BookDao bookDao) {
    //    this.bookDao = bookDao;
    //}

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```



2、修改`applicationContext.xml`，使用<span style="color:red;">`constructor-arg`</span>标签进行依赖注入

<span style="color:red;">注意</span>：`constructor-tag`标签中的`name`属性值映射的是构造方法中的**形参名**！<span style="color:red;">由此可见，构造器注入方式和代码的耦合度较高</span>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <constructor-arg name="bookDao" ref="bookDao"/>
    </bean>
</beans>
```



3、如果要继续注入其他依赖的话，则需要重新修改构造方法和配置文件

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.dao.UserDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    private UserDao userDao;

    public BookServiceImpl(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
        userDao.save();
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
    <bean id="userDao" class="com.stone.dao.impl.UserDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <constructor-arg name="bookDao" ref="bookDao"/>
        <constructor-arg name="userDao" ref="userDao"/>
    </bean>
</beans>
```

最终运行结果如下：

![构造器注入引用类型运行结果](./images/构造器注入引用类型运行结果.png)



4、使用构造器注入简单类型

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    private final int connectionNum;

    private final String databaseName;

    public BookDaoImpl(int connectionNum, String databaseName) {
        this.connectionNum = connectionNum;
        this.databaseName = databaseName;
    }

    @Override
    public void save() {
        System.out.println("book dao save..." + connectionNum + "; " + databaseName);
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
        <constructor-arg name="connectionNum" value="10"/>
        <constructor-arg name="databaseName" value="mysql"/>
    </bean>

    <bean id="userDao" class="com.stone.dao.impl.UserDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl">
        <constructor-arg name="bookDao" ref="bookDao"/>
        <constructor-arg name="userDao" ref="userDao"/>
    </bean>
</beans>
```

最终运行结果如下：

![构造器注入简单类型运行结果](./images/构造器注入简单类型运行结果.png)

<span style="color:red;">拓展Tips：通过调整`applicationContext.xml`文件中`constructor-arg`标签的顺序位置并不会影响程序的运行。</span>



###### 解耦合（了解）

通过构造器注入依赖的方式与代码耦合度较高，因为是通过**形参**进行属性值映射的。

一旦形参名被更改，配置就会失效。为了解耦合，现有以下两种方式：

1、**用参数类型代替参数名**

```xml
<!--解耦合：指定形参类型-->
<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
    <constructor-arg type="int" value="10"/>
    <constructor-arg type="java.lang.String" value="mysql"/>
</bean>
```

**这种方式的缺点**：当出现重复的参数类型时，无法准确映射



2、**用参数索引代替参数名**

```xml
<!--解耦合：指定形参索引-->
<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
    <constructor-arg index="0" value="10"/>
    <constructor-arg index="1" value="mysql"/>
</bean>
```



##### 方式选择

两种依赖注入的方式我们应该如何选择使用？

1、强制依赖（Bean所必须的依赖）使用构造器注入，使用setter注入有概率不进行注入导致null对象出现

2、可选依赖使用setter进行注入，灵活性强

3、Spring框架倡导使用构造器，第三方框架内部大多数采用构造器注入的形式进行Bean的初始化，相对严谨

4、如果有必要可以两者同时使用，使用构造器注入完成强制依赖的注入，使用setter注入完成可选依赖的注入

5、实际开发过程中还要根据实际情况具体分析，如果受控对象没有提供setter方法就必须使用构造器注入

6、<span style="color:red;">自己开发的模块推荐使用setter注入</span>



### 依赖自动装配

IoC容器根据Bean所依赖的资源在容器中自动查找并注入到Bean中的过程称为**自动装配**。

自动装配方式

- <span style="color:red;">按类型（常用）</span>
- 按名称
- 按构造方法
- 不使用自动装配



创建一个新的项目工程`di_autoware`

![依赖自动装配-创建项目工程](./images/依赖自动装配-创建项目工程.png)

项目基本代码内容如下：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {

    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`BookService`

```java
package com.stone.service;

public interface BookService {

    void save();
}
```

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>

    <bean id="bookService" class="com.stone.service.impl.BookServiceImpl"/>
</beans>
```

<span style="color:red;">可以注意到：这次的配置文件中并没有直接声明两个bean的依赖关系，**接下来将使用自动装配实现依赖的自动注入**</span>

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDIAutoware {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookDao.save();
    }
}
```



<span style="color:red;">实现自动装配的方式就是在bean标签中使用`autowire`属性</span>：

```xml
<bean id="bookService" class="com.stone.service.impl.BookServiceImpl" autowire="byType"/>
```

最终运行结果如下：

![依赖自动装配运行结果](./images/依赖自动装配运行结果.png)



其中 `autowire` 属性有两个常用的值：

- **byType**：根据类型自动装配
- **byName**：根据setter名称自动装配



<span style="color:red;">依赖自动装配的特征</span>：

- 自动装配用于引用类型的依赖注入，不能对简单类型进行操作
- 使用按类型装配时（byType）必须保障容器中相同类型的bean唯一，推荐使用
- 使用按名称装配时（byName）必须保障容器中具有指定名称的bean，因变量名与配置耦合，不推荐使用
- <span style="color:blue;">自动装配优先级低于setter注入和构造器注入，同时出现时自动装配配置失效</span>



### 集合注入

<span style="color:red;">集合主要有：数组、List、Set、Map、Properties</span>

创建一个新的项目工程`di_collection`

![集合注入-创建项目工程](./images/集合注入-创建项目工程.png)

项目基本代码内容如下：

`BookDao`

```java
package com.stone.dao;

public interface BookDao {
    void save();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

import java.util.*;

public class BookDaoImpl implements BookDao {

    private int[] arr;

    private List<String> list;

    private Set<String> set;

    private Map<String, String> map;

    private Properties properties;

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void save() {
        System.out.println("book dao save...");

        System.out.println("遍历 数组：" + Arrays.toString(arr));
        System.out.println("遍历 List：" + list);
        System.out.println("遍历 Set：" + set);
        System.out.println("遍历 Map：" + map);
        System.out.println("遍历 Properties：" + properties);
    }
}
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
</beans>
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDICollection {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookDao bookDao = context.getBean("bookDao", BookDao.class);

        bookDao.save();
    }
}
```



<b style="color:red;">接下来需要到配置文件中配置几种类型的注入：</b>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
        <property name="arrName">
            <array>
                <value>100</value>
                <value>200</value>
                <value>300</value>
                <!--对于引用类型的集合对象如User数组，使用ref标签注入User-->
                <!--<ref bean="bookDao"/>-->
            </array>
        </property>
        <property name="listName">
            <list>
                <value>SKT</value>
                <value>RNG</value>
                <value>FPX</value>
            </list>
        </property>
        <property name="setName">
            <set>
                <!--Set会自动去重-->
                <value>Faker</value>
                <value>Faker</value>
                <value>MLXG</value>
                <value>Doinb</value>
            </set>
        </property>
        <property name="mapName">
            <map>
                <entry key="country" value="China"/>
                <entry key="province" value="JiangSu"/>
                <entry key="city" value="NanJing"/>
            </map>
        </property>
        <property name="propertiesName">
            <props>
                    <prop key="username">root</prop>
                    <prop key="password">123456</prop>
            </props>
        </property>
    </bean>
</beans>
```

最终运行结果如下：

![集合注入的运行结果](./images/集合注入的运行结果.png)



### 案例：数据源对象管理

<span style="color:red;">在项目配置文件中管理第三方的Bean</span>

创建一个新的项目工程`datasource_manage`

![数据源对象管理案例-创建项目工程](./images/数据源对象管理案例-创建项目工程.png)

项目代码基本内容如下：

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

main方法

```java
package com.stone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDatasourceManage {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
```



#### Druid

首先要在项目中引入第三方的依赖

`pom.xml`

```xml
<!--Druid数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.23</version>
</dependency>
```

然后在项目的配置文件中配置第三方的Bean

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql:///test"/>
        <property name="username" value="root"/>
        <property name="password" value="1234"/>
    </bean>
</beans>
```

最后在main方法中获取Bean对象并打印

```java
package com.stone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class AppForDatasourceManage {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
    }
}
```

最终运行结果如下：

![数据源对象管理案例运行结果1](./images/数据源对象管理案例运行结果1.png)



#### C3P0

先引入依赖

```xml
<!--C3P0数据源-->
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>
<!--MySQL驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

再修改配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql:///test"/>
        <property name="username" value="root"/>
        <property name="password" value="1234"/>
    </bean>

    <bean id="c3p0DataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql:///c3p0-test"/>
        <property name="user" value="root"/>
        <property name="password" value="1234"/>
    </bean>
</beans>
```

再修改main方法

```java
package com.stone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class AppForDatasourceManage {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //DataSource dataSource = context.getBean("dataSource", DataSource.class);
        //System.out.println(dataSource);
        DataSource c3poDataSource = context.getBean("c3poDataSource", DataSource.class);
        System.out.println(c3poDataSource);
    }
}
```

最终运行结果如下：

![数据源对象管理案例运行结果2](./images/数据源对象管理案例运行结果2.png)



### 加载properties配置信息

https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=18