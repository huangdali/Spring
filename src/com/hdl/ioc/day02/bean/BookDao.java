package com.hdl.ioc.day02.bean;

import org.springframework.stereotype.Component;

/**
 * Created by HDL on 2017/10/17.
 */
@Component("bookDao")
public class BookDao {
    public void add(String context) {
        System.out.println("数据库----add----" + context);
    }
}
