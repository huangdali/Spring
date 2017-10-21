package com.hdl.ioc.day04.dao;

import org.springframework.stereotype.Component;

/**
 * 实际操作用户数据库的地方
 * Created by HDL on 2017/10/21.
 */
@Component("userDao")
public class UserDao {
    public boolean registe(String username, String pwd) {
        if ("admin".equals(username) && "132".equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }
}
