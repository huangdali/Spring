package com.hdl.ioc.day01;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HDL on 2017/10/15.
 */
public class TestIoc {
    @Test
    public void testUser() {
        //1、加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2、通过id拿到对象
        User user = (User) context.getBean("user");
        System.out.println(user);
        user.add();
    }

    @Test
    public void testUser1() {
        //1、加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2、通过id拿到对象
        User user = (User) context.getBean("user1");
        System.out.println(user);
        user.add();
    }

    @Test
    public void testUser2() {
        //1、加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2、通过id拿到对象
        User user1 = (User) context.getBean("user2");
        User user2 = (User) context.getBean("user2");
        System.out.println(user1);
        System.out.println(user2);
//        user.add();
    }

    @Test
    public void testProperty1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PropertyDi propertyDi = (PropertyDi) context.getBean("property1");
        propertyDi.print();

    }

    @Test
    public void testProperty2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PropertyDi propertyDi = (PropertyDi) context.getBean("property2");
        propertyDi.print();
    }

    @Test
    public void testProperty3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        System.out.println("执行service的结果" + userService.add("我去，整个过程就不需要new UserDao（）了"));
    }
}
