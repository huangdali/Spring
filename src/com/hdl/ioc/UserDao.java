package com.hdl.ioc;

/**
 * Created by HDL on 2017/10/16.
 */
public class UserDao {
    public boolean add(String context){
        System.out.println("add--------"+context);
        return true;
    }
}
