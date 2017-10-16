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

1、set方法注入（最常用）

入门：

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




2、构造方法注入

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



3、接口注入(Bean 不支持这种注入)
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




























































