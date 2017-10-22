# Spring学习笔记

---------

## 什么是Spring
### 1、Spring是一个开源的轻量级框架

### 2、Spring核心：

- ioc：控制反转
    - 比如有一个类，在类里面有一个方法（非静态方法），在使用这个类的方法的时候需要通过  new 对象名（） 来实现
    - ioc就是将new对象的过程交给了Spring来做

- aop:面向切面编程
    - 简单来说就是：扩展功能时不需要修改原代码来实现

### 3、Spring是一站式框架

Spring在javaee三层结构中，每一层都提供了不同的解决技术

- web层：Spring MVC

- service层：Sping的ioc

- dao层：Spring的jdbcTemplate

### 4、Spring的版本
目前使用Spring 4.X版本来开发

## IOC学习

### IOC底层实现原理

1、xml配置

2、dom4j解析xml

3、工厂模式

4、反射

图解：

![](https://github.com/huangdali/Spring/blob/master/image/ioc1.png)

### Bean标签常用属性
- id属性：bean使用的名称
    - 不能包含特殊符号
    - 根据id值拿到class配置对象
- class属性：需要注入对象的全路径
- name属性：功能和id属性一样
    - 可以包含一些特殊符号（基本不用）
- scope属性：作用范围（作用域）
    - singleton ：默认值，单例
    - prototype ：多例
    - request :web项目中，Spring创建一个bean的对象，将对象存入到request域中
    - session ：web项目中，Spring创建一个Bean的对象，将对象存入到session域中
    - globalSeesion ：web项目中，应用在porlet环境，如果没有porlet，那么globalsession相当于session

### 属性注入

使用bean创建对象的时候，向类里面注入属性

属性注入的三种方式：

#### 1、set方法注入（最常用）

##### 1.1、入门：

```xml
    <!--属性注入->属性注入-->
    <bean id="property2" class="com.hdl.ioc.PropertyDi">
        <!--name表示类中的属性值-->
        <property name="username" value="hah"/>
    </bean>
```
```java
  @Test
    public void testProperty2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PropertyDi propertyDi = (PropertyDi) context.getBean("property2");
        propertyDi.print();
    }
```
##### 1.2、常用
实际开发中：

```java
/**
* 实际数据库操作的地方
*/
public class UserDao {
    public boolean add(String context){
        System.out.println("insert--------"+context);
        return true;
    }
}

```

//供接口调用的服务类

```java
public class UserService {
    private UserDao userDao;//dao通过set注入，不在需要new对象

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean add(String context) {
        //常规方法--->bean的话不用new了
//        UserDao userDao=new UserDao();
        return userDao.add(context);
    }
}

```

配置

```xml
    <!--属性注入->set属性注入->set对象-->
    <bean id="userDaoConfig" class="com.hdl.ioc.UserDao"/>
    <bean id="userService" class="com.hdl.ioc.UserService">
        <property name="userDao" ref="userDaoConfig">
        </property>
    </bean>
```

使用/servlet调用

```java
    @Test
    public void testProperty3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        System.out.println("执行service的结果: " + userService.add("我去，整个过程就不需要new UserDao（）了"));
    }
```

输出

```java
add--------我去，整个过程就不需要new UserDao（）了
执行service的结果: true
```
##### 1.3、名字空间注入(不常用)

![](https://github.com/huangdali/Spring/blob/master/image/namespace.png)

##### 1.4、复杂类型注入（不常用）

- 数组

![](https://github.com/huangdali/Spring/blob/master/image/arr.png)

- list集合

![](https://github.com/huangdali/Spring/blob/master/image/list.png)

- map集合、properties

![](https://github.com/huangdali/Spring/blob/master/image/map_properties.png)


#### 2、构造方法注入

配置：

```xml
 <bean id="property1" class="com.hdl.ioc.PropertyDi">
        <constructor-arg name="username" value="大力哥">
        </constructor-arg>
    </bean>
```

使用：

```java
    @Test
    public void testProperty1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PropertyConstructer propertyDi = (PropertyConstructer) context.getBean("property1");
        propertyDi.print();
    }
```



#### 3、接口注入(Bean 不支持这种注入)
```java
public interface Dao{
    void delte(String name);
}

public class DaoImpl implements Dao{
    private String name;
    public void delete(String name){
        this.name=name;
    }
}
```

## ioc与di的区别
- ioc ：控制反转，把对象创建交给Spring来配置bean；
- di ：依赖注入，向类里面注入属性；
- 关系 ：di需要不能单独存在，需要在ioc的基础上完成

## Spring整合web开发

1、action中加载配置文件

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```

问题：new 对象，功能可以实现，效率却很低

2、优化思想：把配置加载文件和创建对象的过程，在服务器启动的时候完成

3、实现原理：

3.1、ServletContext对象（存域中）

3.2、监听器（ServletContextListener）

3.3、具体使用：

- 在服务器启动的时候，为每个项目创建servletcontext对象

- 在servletcontext对象创建的时候，使用监听器可以监听servletcontext的生命周期

- 在监听到创建serveltcontext对象的时候，加载Spring配置文件，根据配置文件创建对象

- 把创建出来的对象存到servletcontext域对象里面（调用setAttribute方法）

- 获取对象的时候，到servletcontext域对象里面取即可（调用getAttribute方法）

## 注解

>代码里面标记，使用注解来完成功能 如@Test

1、使用范围：可以使用在类、方法、属性上面

2、达到的效果：可以替代大部分配置文件

### Spring注解开发准备

主要导入aop包

创建配置文件并配置开启注解扫描

```xml
 <!--开启注解扫描
    会根据base-package填写的包名，扫描所有的属性、方法、类上是否有注解
    -->
    <context:component-scan base-package="com.hdl.ioc.day02.bean"/>
    <!--用下面这个配置，只会扫描属性上面的注解-->
    <!--<context:annotation-config></context:annotation-config>-->
```


1、注解创建对象

第一步：在需要对象注入的对象中使用Component注解
```java
@Scope(value = "prototype")//不配置的话默认singleton
@Component(value = "user")//相当于配置<bean id="user">
public class User {
    public void add() {
        System.out.println("add............");
    }
}

```

第二步：测试
```java
 @Test
    public void testUser(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_anno.xml");
        User user= (User) context.getBean("user");
        user.add();
    }
```

扩展：

![](https://github.com/huangdali/Spring/blob/master/image/anno.png)

- @Component
- @Controller
- @Service
- @Repository

功能都一样，都是创建对象


2、注解注入属性

2.1 创建bookdao并注解

```java
@Component("bookDao")
public class BookDao {
    public void add(String context) {
        System.out.println("数据库----add----" + context);
    }
}
```
2.2、创建bookservice并注解，需要注入的对象加上@Autowired注解
```java
@Service(value = "bookService")
public class BookService {
    //拿到dao，作为bookservice属性(不需要实现set方法)
    //第一种、不常用
//    @Autowired //自动注入
//    private BookDao bookDao;

    //第二种，常用--->注入谁更加明确
    @Resource(name = "bookDao")
    private BookDao bookDao;

    public void add(String context) {
        System.out.println("------业务实现----add----------");
        bookDao.add(context);
    }
}

```

2.3、使用

```java
  @Test
    public void testBookService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_anno.xml");
        BookService user = (BookService) context.getBean("bookService");
        user.add("天龙八部");
    }
```
2.4、输出
```
------业务实现----add----------
数据库----add----天龙八部
```
2.5、总结

只需要一行代码配置注解扫描，不像每个都配置bean那么麻烦了

整个过程中，看不到new的过程，这对于后续代码的扩展好处很多

### 混合配置

学习过程中常用的方式（开发中就都用注解会更好点）：

- 1、创建对象一般使用配置文件来完成；

- 2、注入属性一般使用注解来完成

## AOP 面向切面编程
### 概述
效果：扩展功能，可以不通过修改原代码来实现

机制：aop采取**横向抽取机制**，取代传统**纵向继承体系**重复性代码（性能监视、事务管理、安全检查、缓存）

Spring Aop使用纯java实现，不需要专门的编辑过程和类加载器，在运行期间通过代理方式向目标类注入增强代码；

AspecJ是一个基于java语言的aop框架，Spring2.0开始就支持了，AspectJ扩展了java语言，提供了一个专门的编译器，在编译时提供横向代码的注入；

### AOP原理（了解）

纵向机制

![](https://github.com/huangdali/Spring/blob/master/image/zxjz.png)

横向机制

![](https://github.com/huangdali/Spring/blob/master/image/hzcq.png)

### aop操作术语

- Joinpoint（连接点）：类里面的方法可以被增强，这些方法称为连接点

- Pointcut(切入点)：所谓切入点是指我们要对哪些joinpoint（连接点）进行拦截的定义
    - 在类里面可以有很多方法可以被增强，比如实际操作中，只是增强了add和update，实际增强的方法称为切入点。

- Advice（通知/增强）：所谓通知是指拦截到joinpoint之后需要做的申请就是通知，通知分为前置通知、后置通知、异常通知、最终通知、环绕通知（切面要完成的功能）
    - 增强的逻辑，称为增强，比如扩展日志功能
    - 前置通知：方法之前执行
    - 后置通知：方法只会执行
    - 异常通知：方法出现异常
    - 最终通知：在后置之后
    - 环绕通知：在方法之前和之后都执行

- Aspect(切面)：是切入点和通知的结合
    - 把增强应用到具体的切入点方法上的过程
    
![](https://github.com/huangdali/Spring/blob/master/image/other.png)

### aop操作

使用aspectj来实现aop

#### aspectj

本身不是spring的一部分，而是结合spring使用

是一个基于java语言的aop框架，Spring2.0开始就支持了，AspectJ扩展了java语言，提供了一个专门的编译器，在编译时提供横向代码的注入；

### aspectj两种实现方式

操作前，需要导入aop的约束

1、配置xml文件

1.1、使用表达式配置切入点

execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)

- 访问修饰符:private public protacted等，使用*表示任何
- 

eg1：execution(* com.hdl.ioc.day03.Book.add(..)) 类中指定方法

eg2：execution(\* com.hdl.ioc.day03.Book.**(..)) 包中所有方法

eg3：execution(\* \*.*(..)) 所有包中所有方法

eg4：execution(\* save.*(..)) 所有包含save开头的方法

1.2、开始配置

原有类：

```java
public class Book {
    public void add(String name) {
        System.out.println("add-------" + name);
    }
}
```

为book类增加前置增强:

```java
/**
 * book的增强类
 * Created by HDL on 2017/10/18.
 */
public class BookZQ {
    public void before() {
        System.out.println("前置增强.........");
    }
}
```

xml配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--配置对象-->
    <bean id="book" class="com.hdl.ioc.day03.Book"/>
    <!--增强类-->
    <bean id="bookZQ" class="com.hdl.ioc.day03.BookZQ"/>
    <!--配置aop操作-->
    <aop:config>
        <!--配置切入点-->
        <aop:pointcut id="pointcut1" expression="execution(* com.hdl.ioc.day03.Book.*(..))"/>
        <!--配置切面(增强用到方法上)-->
        <aop:aspect ref="bookZQ">
            <!--配置method作用在pointcut上-->
            <aop:before method="before"  pointcut-ref="pointcut1"/>
        </aop:aspect>
    </aop:config>
</beans>
```

测试：

```java
public class TestZQ {
    @Test
    public void testZQ(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_aop.xml");
        Book book= (Book) context.getBean("book");
        book.add("哈利波特");
    }
}
```

输出：
```java
前置增强.........
add-------哈利波特
```

新增方法：

```java
public class Book {
    public void add(String name) {
        System.out.println("add-------" + name);
    }

    public void update(int id) {
        System.out.println("update-------" + id);
    }
}
```
修改表达式为增强所有类的所有方法
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--配置对象-->
    <bean id="book" class="com.hdl.ioc.day03.Book"/>
    <!--增强类-->
    <bean id="bookZQ" class="com.hdl.ioc.day03.BookZQ"/>
   <!--配置aop操作-->
      <aop:config>
          <!--配置切入点，第一个*之后有空格-->
          <aop:pointcut id="pointcut1" expression="execution(* *.*(..))"/>
          <!--配置切面(增强用到方法上)-->
          <aop:aspect ref="bookZQ">
              <!--配置method作用在pointcut上（前置增强）-->
              <aop:before method="before" pointcut-ref="pointcut1"/>
              <!--后置增强-->
              <aop:after-returning method="after" pointcut-ref="pointcut1"/>
              <!--环绕增强-->
              <aop:around method="arund" pointcut-ref="pointcut1"/>
          </aop:aspect>
      </aop:config>
</beans>
```
测试:

```java
public class TestZQ {
    @Test
    public void testZQ(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_aop.xml");
        Book book= (Book) context.getBean("book");
        book.add("哈利波特");
        book.update(1001);
    }
}
```

输出：

```java
前置增强.........
方法前----------------
add-------哈利波特
方法后---------------
后置增强.........


前置增强.........
方法前----------------
update-------1001
方法后---------------
后置增强.........

```


2、注解方式

![](https://github.com/huangdali/Spring/blob/master/image/aop_anno.png)

2.1、配置自动代理和注解
```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--开启注解扫描
        会根据base-package填写的包名，扫描所有的属性、方法、类上是否有注解
   -->
    <context:component-scan base-package="com.hdl.ioc.day05"/>

    <!--自动代理，注解aop-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

</beans>
```
2.2、原类（需要被增强的类）
```java
/**
 * 原类（需要被增强的类）
 * Created by HDL on 2017/10/21.
 */
@Component("book")
public class Book {
    public void add(String bookName) {
        System.out.println("入库操作，操作编号：" + System.currentTimeMillis() + "\t书名：" + bookName);
    }

}

```
2.3 增强类

```java
/**
 * 增强book类
 * Created by HDL on 2017/10/21.
 */
@Aspect//表示是增强类
@Component("bookZQ")
public class BookZQ {
    @Before(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void addZqBefore() {
        System.out.println("入库时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    }

    @After(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void addZqAfte() {
        System.out.println("入库完成时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    }
    @Around(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void around(ProceedingJoinPoint process){
        System.out.println("方法执行前----------------");
        try {
            process.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("方法执行后---------------");

    }
}

```
2.4、测试
```java
public class BookTest {
    @Test
    public void testBook(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_aop_anno.xml");
        Book book= (Book) context.getBean("book");
        book.add("《明朝那些事》");
    }
}

```
2.5、结果
```
方法执行前----------------
入库时间：2017-10-21 23:02:56
入库操作，操作编号：1508598176859	书名：《明朝那些事》
方法执行后---------------
入库完成时间：2017-10-21 23:02:56

```






## Log4J

程序运行过程中记录详细信息

配置：复制配置文件log4j.properties到src文件中

日志级别：

- info 基本信息

- debug 更详细信息

## 一个比较完整的Spring Demo
项目结构图：

![](https://github.com/huangdali/Spring/blob/master/image/struct.png)

结果：

![](https://github.com/huangdali/Spring/blob/master/image/out.png)

### 导入必要的jar包(见结构图)
### 创建并配置spring的配置文件：applicationContext_web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--开启注解扫描
   会根据base-package填写的包名，扫描所有的属性、方法、类上是否有注解
   -->
    <context:component-scan base-package="com.hdl.ioc.day04"/>
    <!--用下面这个配置，只会扫描属性上面的注解-->
    <!--<context:annotation-config></context:annotation-config>-->
</beans>
```
### 配置web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <!--配置Spring监听器，自动加载配置文件(避免每次访问servlet都需要加载一次配置文件，浪费资源)-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext_web.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</web-app>
```

### 创建userservlet
```java
@WebServlet("/UserServlet")
public class UserServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        System.out.println(username+"\n"+pwd);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserService userService = (UserService) context.getBean("userService");
        boolean registe = userService.registe(username, pwd);
        PrintWriter pw = response.getWriter();
        pw.write("username=" + username + "\n");
        pw.write("pwd=" + pwd + "\n");
        pw.write("是否注册成功：" + registe);
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
```

### 创建UserService

```java
/**
 * 实际提供用户操作逻辑的地方
 * Created by HDL on 2017/10/21.
 */
@Service("userService")
public class UserService {
    //    @Autowired
    @Resource(name = "userDao")
    private UserDao userDao;

    public  boolean registe(String username, String pwd) {
        if (username == null || "".equals(username) || pwd == null || "".equals(pwd)) {
            return false;
        }
        return userDao.registe(username, pwd);
    }
}

```

### 创建UserDao

```java
/**
 * 实际操作用户数据库的地方
 * Created by HDL on 2017/10/21.
 */
@Component("userDao")
public class UserDao {
    public boolean registe(String username, String pwd) {
        if ("admin".equals(username) && "132".equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }
}

```



















































































