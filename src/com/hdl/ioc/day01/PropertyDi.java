package com.hdl.ioc.day01;

/**
 * Created by HDL on 2017/10/16.
 */
public class PropertyDi {
    private String username;

    public PropertyDi() {
    }

    public PropertyDi(String username) {
        this.username = username;
    }

    public void print() {
        System.out.println("username=" + username);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
