package com.hdl.ioc.day03;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * book的增强类
 * Created by HDL on 2017/10/18.
 */
public class BookZQ {
    public void before() {
        System.out.println("\n前置增强.........");
    }
    public void after(){
        System.out.println("后置增强.........\n");
    }
    public void arund(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("方法前----------------");
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("方法后---------------");
    }
}
