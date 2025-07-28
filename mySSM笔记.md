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



#### 加载properties配置信息

<span style="color:red;">我们尝试加载properties文件，并从中读取配置的属性值</span>

首先在`applicationContext.xml`中开启context命名空间；

然后使用context命名标签加载指定的properties文件，并使用`${}`读取加载的属性值；

`jdbc.properties`

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql:///test
jdbc.username=root
jdbc.password=1234
```

`applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!--1.开启context命名空间-->
    <!--2.使用context空间加载properties文件-->
    <!--3.使用属性占位符${}读取properties文件中的属性-->
    <context:property-placeholder location="jdbc.properties"/>

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!--<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">-->
    <!--    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>-->
    <!--    <property name="url" value="jdbc:mysql:///test"/>-->
    <!--    <property name="username" value="root"/>-->
    <!--    <property name="password" value="1234"/>-->
    <!--</bean>-->

    <!--<bean id="c3p0DataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">-->
    <!--    <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>-->
    <!--    <property name="jdbcUrl" value="jdbc:mysql:///c3p0-test"/>-->
    <!--    <property name="user" value="root"/>-->
    <!--    <property name="password" value="1234"/>-->
    <!--</bean>-->
</beans>
```

接着修改main方法，验证properties中的属性值是否读取成功：

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class AppForDatasourceManage {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //DataSource dataSource = context.getBean("dataSource", DataSource.class);
        //System.out.println(dataSource);

        //DataSource c3poDataSource = context.getBean("c3p0DataSource", DataSource.class);
        //System.out.println(c3poDataSource);

        BookDao bookDao = context.getBean("bookDao", BookDao.class);
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

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void save() {
        System.out.println("book dao save...");
        System.out.println("driverClassName: " + driverClassName);
        System.out.println("url: " + url);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
    }
}
```

最终运行结果如下：

![加载properties文件运行结果](./images/加载properties文件运行结果.png)



**注意：**

- 不加载系统属性

```xml
<context:property-placeholder location="jdbc.properties" system-properties-mode="NEVER"/>
```



- 加载多个properties文件

```xml
<!--逗号写法-->
<context:property-placeholder location="jdbc.properties, jdbc2.properties"/>
<!--规范写法-->
<context:property-placeholder location="*.properties"/>
```



- 加载所有properties文件

```xml
<context:property-placeholder location="classpath:*.properties" system-properties-mode="NEVER"/>
```



- 从类路径或jar包中搜索并加载properties文件

```xml
<context:property-placeholder location="classpath*:*.properties" system-properties-mode="NEVER"/>
```





### 容器操作

创建一个新的项目工程`container`

![容器相关操作-创建项目工程](./images/容器相关操作-创建项目工程.png)

项目代码基本内容如下：

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

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>
</beans>
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForContainer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();
    }
}
```



有关容器的两个常用操作：

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AppForContainer {
    public static void main(String[] args) {
        //加载配置文件（创建容器）
        //1.加载类路径下的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.加载文件系统下的配置文件
        //ApplicationContext context = new FileSystemXmlApplicationContext("D:\\...\\applicationContext.xml");
        //3.加载多个配置文件
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml", "bean2.xml");
        //ApplicationContext context = new FileSystemXmlApplicationContext("...", "...");

        //获取bean
        //1.使用Bean名称获取
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        //2.使用Bean名称获取并指定类型
        //BookDao bookDao = context.getBean("bookDao", BookDao.class);
        //3.使用Bean类型获取（需保证容器中对应类型的Bean是唯一的）
        //BookDao bookDao = context.getBean(BookDao.class);

        bookDao.save();
    }
}
```



#### 容器类层次结构

关于`ApplicationContext`类的结构分析：通过IDEA的**Hierarchy**功能可以看到`ApplicationContext`类的结构层次如下

![ApplicationContext类的层次结构](./images/ApplicationContext类的层次结构.png)

其中：

- `BeanFactory`是最顶层的接口
- `ApplicationContext`是常用的接口
- `ConfigurableApplicationContext`提供了关闭容器功能`close()`方法
- `ClassPathXmlApplicationContext`是常用实现类



#### BeanFactory

`BeanFactory`是Spring最早期的容器初始化方案，它与`ApplicationContext`的区别见如下代码：

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AppForBeanFactory {
    public static void main(String[] args) {
        Resource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory bf = new XmlBeanFactory(resource);
        BookDao bookDao = bf.getBean(BookDao.class);
        bookDao.save();
    }
}
```

<span style="color:red;">BeanFactory与ApplicationContext的区别在于：两者加载Bean的时机不同。</span>

我们在`BookDao`中设置构造函数，并查看`ApplicationContext`和`BeanFactory`调用它的时机：

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;

public class BookDaoImpl implements BookDao {

    public BookDaoImpl() {
        System.out.println("constructor");
    }

    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
}
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AppForContainer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
```

运行结果如下：

![ApplicationContext加载Bean的时机](./images/ApplicationContext加载Bean的时机.png)



main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AppForBeanFactory {
    public static void main(String[] args) {
        Resource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory bf = new XmlBeanFactory(resource);
    }
}
```

运行结果如下：

![BeanFactory加载Bean的时机](./images/BeanFactory加载Bean的时机.png)

<span style="color:red;">由此可见 BeanFactory 是延迟加载Bean，即Bean只有在被获取使用的时候才会被初始化；而 ApplicationContext 是立即加载Bean，即在启动容器的时候Bean就已经被初始化好了。</span>



通过在配置文件中设置`lazy-init`，也可以让`ApplicationContext`实现延迟加载Bean：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl" lazy-init="true"/>
</beans>
```



### 核心容器总结

#### 容器相关

> BeanFactory是IoC容器的顶层接口，初始化BeanFactory对象时，加载的Bean是延时加载
>
> ApplicationContext接口是Spring容器的核心接口，初始化时Bean立即加载
>
> ApplicationContext接口提供基础的Bean操作相关方法，通过其他接口扩展其功能
>
> ApplicationContext接口常用初始化类
>
> - ClassPathXmlApplicationContext
> - FileSystemXmlApplicationContext



#### Bean相关

![bean标签的属性](./images/bean标签的属性.png)



#### 依赖注入相关

![依赖注入相关标签](./images/依赖注入相关标签.png)



### 注解开发

<span style="color:red;">在Spring中，使用注解可以简化配置，加快开发速度。</span>

从Spring2.0开始逐步提供了各种各样的注解，到Spring2.5注解已经基本完善。到了Spring3.0推出了纯注解开发。

#### 注解开发定义Bean

创建一个新的项目工程`annotation_bean`

![注解开发-创建项目工程](./images/注解开发-创建项目工程.png)

项目代码基本内容如下：

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

</beans>
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();
    }
}
```



首先将配置文件中的Bean配置改造成注解的形式：<span style="color:red;">在类上添加相应的注解，并在配置文件中配置注解扫描区</span>

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Component;

@Component("bookDao")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`applicationContext.xml`

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--<bean id="bookDao" class="com.stone.dao.impl.BookDaoImpl"/>-->

    <!--扫描指定包及其子包中的类，将类中添加了注解的类作为bean对象进行管理-->
    <context:component-scan base-package="com.stone"/>
</beans>
```

接着如法炮制，将`BookService`也利用注解注册成Bean：

`BookServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.stereotype.Component;

@Component // 这里没有指定Bean的名称
public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }
}
```

main方法

```java
package com.stone;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();

        // 由于没有在注解中设置Bean的名称，所以这里应该通过类型类获取Bean
        BookService bookService = context.getBean(BookService.class);
        System.out.println(bookService);
    }
}
```

最终运行结果如下：

![注解注册Bean运行结果](./images/注解注册Bean运行结果.png)



##### 衍生注解

Spring提供了@Component注解的三个衍生注解：

- @Controller：用于表现层Bean定义
- @Service：用于业务层Bean定义
- @Repository：用于数据层Bean定义

```java
@Repository("bookDao")
public class BookDaoImpl implements BookDao {}

@Service
public class BookServiceImpl implements BookService {}
```





#### Spring纯注解开发

Spring3.0升级了纯注解开发模式，使用Java类替代配置文件，开启了Spring快速开发赛道。

<span style="color:red;">通过以下配置类即可实现之前的配置文件效果</span>

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 声明这是一个Spring配置类
@ComponentScan(basePackages = {"com.stone"}) // 配置注解扫描包
public class SpringConfig {
}
```

同时修改main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppForAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();
    }
}
```

最终运行结果如下：

![基于配置类的纯注解开发运行结果](./images/基于配置类的纯注解开发运行结果.png)



#### Bean的管理

创建一个新的项目工程`annotation_bean_manager`

![annotation_bean_manager](./images/annotation_bean_manager.png)

项目代码基本内容如下：（<span style="color:red;">本项目不再创建xml配置文件</span>）

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
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.stone"})
public class SpringConfig {
}
```

main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao1 = context.getBean(BookDao.class);
        BookDao bookDao2 = context.getBean(BookDao.class);
        System.out.println(bookDao1);
        System.out.println(bookDao2);
    }
}
```



**Bean作用范围**

运行程序可知容器中的Bean是**单例**的：

![annotation_bean_manager运行结果](./images/annotation_bean_manager运行结果.png)

<span style="color:red;">通过在BookDaoImpl类中添加@Scope注解来控制容器中的Bean是否为单例模式</span>

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```

最终运行结果如下：

![非单例模式Bean的运行结果](./images/非单例模式Bean的运行结果.png)



**Bean生命周期**

<span style="color:red;">通过在自定义方法上添加@PostConstructor注解和@PreDestroy注解来控制Bean的生命周期</span>

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
// @Scope("prototype")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }

    @PostConstruct
    public void customInit() {
        System.out.println("book dao init...");
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("book dao destroy...");
    }
}
```

同时修改main方法，调用容器的关闭钩子方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        BookDao bookDao1 = context.getBean(BookDao.class);
        BookDao bookDao2 = context.getBean(BookDao.class);
        System.out.println(bookDao1);
        System.out.println(bookDao2);

        context.close();
    }
}
```

最终运行结果如下：

![通过注解控制Bean的生命周期](./images/通过注解控制Bean的生命周期.png)



#### 依赖注入

**自动装配**

创建一个新的项目工程`annotation_di`

![annotatio_di](./images/annotatio_di.png)

项目代码基本内容如下：（<span style="color:red;">本项目不再创建xml配置文件</span>）

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.stone")
public class SpringConfig {
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
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
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
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        System.out.println("service save...");
        bookDao.save();
    }
}
```

main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookService bookService = context.getBean(BookService.class);
        bookService.save();
    }
}
```

初次运行项目会得到如下结果：<span style="color:red;">这是因为没有了配置文件，就没有了两个Bean之间依赖关系的声明</span>

![annotation_di运行结果1](./images/annotation_di运行结果1.png)



##### 根据类型注入

<b style="color:red;">要想解决这个问题，就需要用到@Autowired注解</b>

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    // 由于@Autowired注解是用反射机制中的暴力反射直接给属性赋值，所以不再需要setter方法
    // public void setBookDao(BookDao bookDao) {
    //     this.bookDao = bookDao;
    // }

    public void save() {
        System.out.println("service save...");
        bookDao.save();
    }
}
```

再次运行项目会得到如下结果：

![annotation_di运行结果2](./images/annotation_di运行结果2.png)



##### 根据名称注入

<b style="color:red;">当BookDao接口存在多个实现类时，此时通过注解根据类型自动注入就会报错，因此要改为根据名称注入</b>

![annotation_di运行结果3](./images/annotation_di运行结果3.png)

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`BookDaoImpl2`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository("bookDao2")
public class BookDaoImpl2 implements BookDao {
    public void save() {
        System.out.println("book dao save...2");
    }
}
```

再次运行项目会得到如下结果：

![annotation_di运行结果4](./images/annotation_di运行结果4.png)

**拓展**：

①：

自动装配基于<span style="color:blue;">反射设计</span>创建对象，并暴力反射对应属性，为私有属性初始化数据，因此无需提供setter方法；

自动装配建议使用<span style="color:blue;">无参构造方法</span>创建对象（默认），如果不提供对应构造方法，请提供唯一的构造方法；

②：

除了可以通过**更改属性名称**来切换注入的Bean类型之外，还可以通过**@Qualifier**注解来实现

```java
package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    @Qualifier("bookDao2")
    private BookDao bookDao;

    // 由于@Autowired注解是用反射机制暴力反射直接给私有属性赋值，所以不再需要setter方法
    // public void setBookDao(BookDao bookDao) {
    //     this.bookDao = bookDao;
    // }

    public void save() {
        System.out.println("service save...");
        bookDao.save();
    }
}
```



##### 简单类型注入

使用**@Value**注解可以实现简单类型的注入

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {
    @Value("SpringStone")
    private String name;

    public void save() {
        System.out.println("book dao save..." + name);
    }
}
```



##### 加载properties文件

在`resources`目录下新建jdbc.properties文件

```properties
name=SpringStone
```

在配置类中配置加载properties文件（<span style="color:red;">注意：路径仅支持单一文件配置，多文件请使用逗号隔开；不允许使用通配符*</span>）

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.stone")
@PropertySource({"classpath:jdbc.properties"}) // 加载项目路径下的properties文件
public class SpringConfig {
}
```

在类中使用占位符`${}`注入属性

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {
    @Value("${name}")
    private String name;

    public void save() {
        System.out.println("book dao save..." + name);
    }
}
```



#### 第三方Bean管理

创建一个新的项目工程`annotation_third_bean_manager`

![annotation_third_bean_manager](./images/annotation_third_bean_manager.png)

项目代码基本内容如下：

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
}
```

main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}
```



##### 三方Bean管理

<span style="color:red;">由于无法直接把Bean的管理注解写在第三方类中，所以我们需要在配置类中间接管理</span>

首先导入三方依赖：

```xml
<!--Druid数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.23</version>
</dependency>
```

然后在配置类中编码：

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.cj.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///test");
        ds.setUsername("root");
        ds.setPassword("1234");
        return ds;
    }
}
```

最后在main方法中获取Bean：

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.cj.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///test");
        ds.setUsername("root");
        ds.setPassword("1234");
        return ds;
    }
}
```

最终运行结果如下：

![annotation_third_bean_manager运行结果1](./images/annotation_third_bean_manager运行结果1.png)



<span style="color:red;">如果所有的三方Bean都放在Spring的配置类中管理，就会让这个类变得臃肿，所以我们可以将这些部分抽离出来</span>

首先抽离出`JdbcConfig`

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.cj.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///test");
        ds.setUsername("root");
        ds.setPassword("1234");
        return ds;
    }

}
```

然后利用**@Import**注解在Spring配置类中导入`JdbcConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JdbcConfig.class})
public class SpringConfig {
}
```



##### 注入属性值

简单类型属性值注入：使用**@Value**注解

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    // 简单类型属性值注入
    @Value("com.cj.mysql.jdbc.Driver")
    private String driver;
    @Value("jdbc:mysql:///test")
    private String url;
    @Value("root")
    private String username;
    @Value("1234")
    private String password;

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

}
```



引用类型属性值注入：使用**自动装配**搭配方法**形参**

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
import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }
}
```

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JdbcConfig.class})
@ComponentScan(basePackages = {"com.stone"})
public class SpringConfig {
}
```

`JdbcConfig`

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.stone.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    // 通过成员变量注入简单类型
    @Value("com.cj.mysql.jdbc.Driver")
    private String driver;
    @Value("jdbc:mysql:///test")
    private String url;
    @Value("root")
    private String username;
    @Value("1234")
    private String password;

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource(BookDao bookDao) {
        // 通过自动装配注入引用类型
        System.out.println(bookDao);
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

}
```

最终运行结果如下：

![annotation_third_bean_manager运行结果2](./images/annotation_third_bean_manager运行结果2.png)



#### 总结

xml配置对比注解配置

![xml配置对比注解配置](./images/xml配置对比注解配置.png)







## Spring整合MyBatis

首先创建一个新的项目工程`spring_mybatis`

![spring_mybatis](./images/spring_mybatis.png)

项目代码基本内容如下：<span style="color:red;">是关于Mybatis独立开发的内容</span>

`t_account.sql`

```sql
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `money` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `t_account` (name, money) value ('Tom', 1000);
insert into `t_account` (name, money) value ('Jerry', 500);
```

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.stone</groupId>
  <artifactId>spring_mybatis</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>spring_mybatis</name>

  <dependencies>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.7</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.16</version>
    </dependency>
  </dependencies>
</project>
```

`jdbc.properties`

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql:///spring_stone?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=1234
```

`SqlMapConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <typeAliases>
        <package name="com.stone.domain"/>
    </typeAliases>
    <!--配置环境-->
    <environments default="mysql">
        <environment id="mysql">
            <!--事务管理器-->
            <transactionManager type="JDBC"/>
            <!--数据源-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--映射文件-->
    <mappers>
        <package name="com.stone.dao"/>
    </mappers>
</configuration>
```

`Account`

```java
package com.stone.domain;

public class Account {

    private Integer id;
    private String name;
    private Double money;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
```

`AccountDao`

```java
package com.stone.dao;

import com.stone.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountDao {

    @Insert("insert into t_account(name,money) values(#{name},#{money})")
    void save(Account account);

    @Update("update t_account set name=#{name},money=#{money} where id=#{id}")
    void update(Account account);

    @Delete("delete from t_account where id=#{id}")
    void delete(Integer id);

    @Select("select * from t_account")
    List<Account> findAll();

    @Select("select * from t_account where id=#{id}")
    Account findById(Integer id);
}
```

main方法

```java
package com.stone;

import com.stone.dao.AccountDao;
import com.stone.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) throws IOException {
        // 1. 创建 SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 2. 加载配置文件 SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 3. 创建 SqlSessionFactory 对象
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 4. 创建 SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 5. 执行操作
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        Account account = accountDao.findById(1);
        System.out.println(account);
        // 6. 释放资源
        sqlSession.close();
    }
}
```



<b style="color:red;">从上面的代码分析应该将Mybatis的哪些对象交给Spring管理成Bean</b>

![Mybatis代码分析](./images/Mybatis代码分析.png)

由上图可知，初始化并加载了`SqlMapConfig.xml`中配置信息的`SqlSessionFactory`对象是**核心对象**，应该交给Spring统一管理！

而`SqlMapConfig.xml`中的配置信息分析如下：

![SqlMapConfig配置分析](./images/SqlMapConfig配置分析.png)



### 整合

首先添加相关依赖

```xml
<!--spring-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.27</version>
</dependency>
<!--druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.23</version>
</dependency>
<!--spring-jdbc依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.27</version>
</dependency>
<!--spring整合mybatis-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.0</version>
</dependency>
```

然后做好Spring注解开发的相关配置

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.stone"})
@PropertySource({"classpath:jdbc.properties"})
@Import(JdbcConfig.class)
public class SpringConfig {
}
```

`JdbcConfig`

```java
package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
}
```

在`AccountDao`中添加**@Repository**注解

```java
@Repository
public interface AccountDao {
    ...
}
```

`AccountService`

```java
package com.stone.service;

import com.stone.domain.Account;

import java.util.List;

public interface AccountService {
    void save();

    void update(Account account);

    void delete(Integer id);

    Account findById(Integer id);

    List<Account> findAll();
}
```

`AccountServiceImpl`

```java
package com.stone.service.impl;

import com.stone.dao.AccountDao;
import com.stone.domain.Account;
import com.stone.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public List<Account> findAll() {
        return accountDao.findAll();
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    public void delete(Integer id) {
        accountDao.delete(id);
    }

    public void update(Account account) {
        accountDao.update(account);
    }

    public void save(Account account) {
        accountDao.save(account);
    }
}
```

<span style="color:red;">接下来就要配置MyBatis的Bean管理，并抽离成`MybatisConfig`类</span>

```java
package com.stone.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class MybatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        //spring整合mybatis依赖中提供了SqlSessionFactoryBean对象，可以让我们快速地创建SqlSessionFactory对象。
        //通过查看SqlSessionFactoryBean的源码，发现该类实现了FactoryBean接口，且泛型为SqlSessionFactory。
        SqlSessionFactoryBean ssfBean = new SqlSessionFactoryBean();
        ssfBean.setTypeAliasesPackage("com.stone.domain");
        ssfBean.setDataSource(dataSource);
        return ssfBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.stone.dao");
        return msc;
    }
}
```

![MyBatis的xml配置文件和配置类的对应关系](./images/MyBatis的xml配置文件和配置类的对应关系.png)

在`SpringConfig`中导入`MybatisConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.stone"})
@PropertySource({"classpath:jdbc.properties"})
@Import({JdbcConfig.class, MybatisConfig.class})
public class SpringConfig {
}
```

重新编写main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.domain.Account;
import com.stone.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App2 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        Account account = accountService.findById(1);
        System.out.println(account);
    }
}
```

最终运行结果如下：

![spring_mybatis运行结果](./images/spring_mybatis运行结果.png)







## Spring整合JUnit

<span style="color:blue;">在之前的`spring_mybatis`项目工程中，继续整合JUnit</span>

首先导入依赖

```xml
<!--junit-->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<!--spring整合junit-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.16</version>
</dependency>
```

然后在`test`目录下创建测试类

`AccountServiceTest`

```java
package com.stone.service;

import com.stone.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //设定类运行器，告诉junit使用spring的测试框架
@ContextConfiguration(classes = SpringConfig.class) //加载spring配置文件
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testFindById() {
        System.out.println(accountService.findById(2));
    }

    @Test
    public void testFindAll() {
        System.out.println(accountService.findAll());
    }
}
```

最终运行结果如下：

![spring整合junit运行结果](./images/spring整合junit运行结果.png)







## AOP

### 核心概念 TODO

AOP（Aspect Oriented Programming）面向切面编程，一种编程范式，知道开发者如何组织程序结构

- OOP（Object Oriented Programming）面向对象编程

<span style="color:red;">作用：在**不改动原始设计的基础上**为其进行**功能增强**</span>

<span style="color:red;">Spring理念：**无侵入式编程**</span>

实例代码：首先创建一个新的项目工程`aop_demo`

![aop_demo](./images/aop_demo.png)

项目代码基本内容如下：

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.stone</groupId>
  <artifactId>aop_demo</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>aop_demo</name>

  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.27</version>
    </dependency>
  </dependencies>
</project>
```

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.stone"})
public class SpringConfig {
}
```

`BookDao`

```java
package com.stone.dao;

public interface BookDao {
    void save();

    void update();

    void delete();

    void select();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    public void save() {
        // 记录程序执行开始时间
        Long startTime = System.currentTimeMillis();
        // 业务执行
        for (int i = 0; i < 10000; i++) {
            System.out.println("book dao save...");
        }
        // 记录程序执行结束时间
        Long endTime = System.currentTimeMillis();
        // 计算程序执行时间
        System.out.println("程序执行时间：" + (endTime - startTime) + "ms");
    }

    public void select() {
        System.out.println("book dao select...");
    }

    public void delete() {
        System.out.println("book dao delete...");
    }

    public void update() {
        System.out.println("book dao update...");
    }
}
```

main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = context.getBean(BookDao.class);
        bookDao.save();
    }
}
```

最终运行结果如下：



可以看到，**AOP**让我们可以在不改动源代码的情况下，给其他方法也加上了“计算执行时间”的操作。

从上面的代码，我们可以分析出**AOP的核心概念**：

![AOP核心概念](./images/AOP核心概念.png)



#### 总结

<b style="color:red;">连接点（JoinPoint）：程序执行过程中的任意位置，粒度为执行方法、抛出异常、设置变量等</b>

- 在SpringAOP中，理解为方法的执行

<b style="color:red;">切入点（PointCut）：匹配连接点的式子</b>

- 在SpringAOP中，一个切入点可以只描述一个具体方法，也可以匹配多个方法
    - 一个具体方法：com.xxx.xxx包下的**BookDao**接口中定义的无形参、无返回值的**save**方法
    - 匹配多个方法：所有的**save**方法；所有的get开头的方法；所有以Dao结尾的接口中定义的任意方法；所有只有一个形参的方法...

<b style="color:red;">通知（Advice）：在切入点处执行的操作，也就是共性功能</b>

- 在SpringAOP中，功能最终以方法的形式呈现

<b style="color:red;">通知类：定义通知的类</b>

<b style="color:red;">切面（Aspect）：描述通知与切入点的对应关系</b>



### 入门案例

需求：通过AOP实现在接口方法执行前输出当前系统时间

<span style="color:blue;">思路分析</span>：使用**注解**开发

1.导入依赖

2.制作连接点方法：Dao接口与实现类

3.制作共性功能：通知类与通知

4.定义切入点

5.绑定切入点与通知的关系（切面）



<span style="color:blue;">具体实现</span>：

创建一个新的项目工程`aop_quickstart`

![aop_quickstart](./images/aop_quickstart.png)

项目代码基本内容如下：

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.stone</groupId>
  <artifactId>aop_quickstart</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>aop_quickstart</name>

  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.27</version>
    </dependency>
  </dependencies>
</project>
```

`SpringConfig`

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.stone")
public class SpringConfig {
}
```

`BookDao`

```java
package com.stone.dao;

public interface BookDao {
    void save();

    void update();
}
```

`BookDaoImpl`

```java
package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    public void update() {
        System.out.println("book dao update...");
    }

    public void save() {
        System.out.println(System.currentTimeMillis());
        System.out.println("book dao save...");
    }
}
```

main方法

```java
package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = context.getBean(BookDao.class);
        bookDao.save();
        bookDao.update();
    }
}
```



首先，需要导入AOP相关的依赖：<span style="color:red;">由于Spring中已经内置了AOP的核心依赖，所以我们只需要导入`aspectjweaver`依赖即可</span>

![Spring内置aop依赖](./images/Spring内置aop依赖.png)

```xml
<!--切面编程相关依赖-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.24</version>
</dependency>
```

然后我们需要定义一个通知类`MyAdvice`

```java
package com.stone.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component // 将该类交给Spring作为Bean管理
@Aspect // 声明该类是一个切面类
public class MyAdvice {

    // 定义通知（前置通知）
    @Before("pointcut()") // 定义切面，绑定切入点和通知的关系
    public void getSysTime() {
        System.out.println("系统时间：" + System.currentTimeMillis());
    }

    // 定义切入点
    @Pointcut("execution(void com.stone.dao.BookDao.update())")
    private void pointcut() {}
}
```

接着需要在`SpringConfig`中开启AOP注解支持

```java
package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.stone")
@EnableAspectJAutoProxy // 开启AOP注解支持
public class SpringConfig {
}
```

最终运行结果如下：

![aop_quickstart运行结果](./images/aop_quickstart运行结果.png)



### AOP工作流程

1.Spring容器启动

2.读取所有**切面**中配置的**切入点**

3.初始化Bean，判断Bean对应的类中的方法是否匹配到任意**切入点**

- 匹配失败，直接创建对象
- 匹配成功，创建原始对象（<span style="color:red;">目标对象</span>）的<span style="color:red;">代理对象</span>

4.获取Bean执行方法

- 获取Bean，调用方法执行，完成操作
- 获取Bean的代理对象，根据代理对象的运行模式运行原始方法与增强内容，完成操作



## End

https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.788.player.switch&vd_source=71b23ebd2cd9db8c137e17cdd381c618&p=33
