package com.hdl.ioc.day04.service;

import com.hdl.ioc.day04.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 实际提供用户操作逻辑的地方
 * Created by HDL on 2017/10/21.
 */
@Service("userService")
public class UserService {
    //    @Autowired
    @Resource(name = "userDao")
    private UserDao userDao;

    public  boolean registe(String username, String pwd) {
        if (username == null || "".equals(username) || pwd == null || "".equals(pwd)) {
            return false;
        }
        return userDao.registe(username, pwd);
    }
}
