package com.hdl.ioc.day01;

/**
 * Created by HDL on 2017/10/15.
 */
public class UserFactory {
    public static User getUser() {
        return new User();
    }
}
