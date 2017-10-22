package com.hdl.ioc.day05;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HDL on 2017/10/21.
 */
public class BookTest {
    @Test
    public void testBook(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_aop_anno.xml");
        Book book= (Book) context.getBean("book");
        book.add("《明朝那些事》");
    }
}
