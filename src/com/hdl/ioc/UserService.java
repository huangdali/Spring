package com.hdl.ioc;

/**
 * Created by HDL on 2017/10/16.
 */
public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean add(String context) {
        //常规方法--->bean的话不用new了
//        UserDao userDao=new UserDao();
        return userDao.add(context);
    }
}
