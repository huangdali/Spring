package com.hdl.ioc.day01;

import org.springframework.stereotype.Component;

/**
 * Created by HDL on 2017/10/16.
 */
@Component(value = "userDao")
public class UserDao {
    public boolean add(String context){
        System.out.println("add--------"+context);
        return true;
    }
}
