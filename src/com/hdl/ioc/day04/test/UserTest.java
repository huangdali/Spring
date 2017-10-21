package com.hdl.ioc.day04.test;

import com.hdl.ioc.day04.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HDL on 2017/10/21.
 */
public class UserTest {
    @Test
    public void testUser(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_web.xml");
        UserService userService= (UserService) context.getBean("userService");
        userService.add();
    }
}
