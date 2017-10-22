package com.hdl.ioc.day05;

import org.springframework.stereotype.Component;

/**
 * 原类（需要被增强的类）
 * Created by HDL on 2017/10/21.
 */
@Component("book")
public class Book {
    public void add(String bookName) {
        System.out.println("入库操作，操作编号：" + System.currentTimeMillis() + "\t书名：" + bookName);
    }

}
