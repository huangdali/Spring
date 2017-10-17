package com.hdl.ioc.day02;

import com.hdl.ioc.day02.bean.BookService;
import com.hdl.ioc.day02.bean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HDL on 2017/10/17.
 */
public class TestAnno {
    @Test
    public void testUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_anno.xml");
        User user = (User) context.getBean("user");
        user.add();
    }

    @Test
    public void testBookService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_anno.xml");
        BookService user = (BookService) context.getBean("bookService");
        user.add("天龙八部");
    }
}
