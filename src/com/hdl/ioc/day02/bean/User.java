package com.hdl.ioc.day02.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by HDL on 2017/10/17.
 */
@Scope(value = "prototype")//不配置的话默认singleton
@Component(value = "user")//相当于配置<bean name="user">
public class User {
    public void add() {
        System.out.println("add............");
    }
}
