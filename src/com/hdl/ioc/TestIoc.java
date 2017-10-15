package com.hdl.ioc;

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
        User user = (User) context.getBean("user2");
        System.out.println(user);
        user.add();
    }
}
