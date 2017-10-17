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

开发中常用的方式（个人觉得都用注解会更好点）：

- 1、创建对象一般使用配置文件来完成；

- 2、注入属性一般使用注解来完成

















































































