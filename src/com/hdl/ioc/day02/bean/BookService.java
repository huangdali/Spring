package com.hdl.ioc.day02.bean;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by HDL on 2017/10/17.
 */
@Service(value = "bookService")
public class BookService {
    //拿到dao，作为bookservice属性(不需要实现set方法)
    //第一种、不常用
//    @Autowired //自动注入
//    private BookDao bookDao;

    //第二种，常用--->注入谁更加明确
    @Resource(name = "bookDao")
    private BookDao bookDao;

    public void add(String context) {
        System.out.println("------业务实现----add----------");
        bookDao.add(context);
    }
}
