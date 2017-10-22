package com.hdl.ioc.day05;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 增强book类
 * Created by HDL on 2017/10/21.
 */
@Aspect//表示是增强类
@Component("bookZQ")
public class BookZQ {
    @Before(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void addZqBefore() {
        System.out.println("入库时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    }

    @After(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void addZqAfte() {
        System.out.println("入库完成时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    }
    @Around(value = "execution(* com.hdl.ioc.day05.*.*(..))")
    public void around(ProceedingJoinPoint process){
        System.out.println("方法执行前----------------");
        try {
            process.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("方法执行后---------------");

    }
}
