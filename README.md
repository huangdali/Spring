# Spring学习笔记

---------

## 什么是Spring
### 1、Spring是一个开源的轻量级框架

### 2、Spring核心：

- ioc：控制反转
    - 比如有一个类，在类里面有一个方法（非静态方法），在使用这个类的方法的时候需要通过  new 对象名（） 来实现
    - ioc就是将new对象的过程交给了Spring来做

- aop:面向切面编程
    - 简单来说就是：扩展功能时不需要修改源代码来实现

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