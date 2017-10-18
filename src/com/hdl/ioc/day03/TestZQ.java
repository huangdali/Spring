package com.hdl.ioc.day03;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HDL on 2017/10/18.
 */
public class TestZQ {
    @Test
    public void testZQ(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_aop.xml");
        Book book= (Book) context.getBean("book");
        book.add("哈利波特");
        book.update(1001);
        System.out.println(book.toString());
    }
}
