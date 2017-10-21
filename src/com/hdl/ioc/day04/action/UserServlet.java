package com.hdl.ioc.day04.action;

import com.hdl.ioc.day04.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户接口
 * Created by HDL on 2017/10/21.
 */
@WebServlet("/UserServlet")
public class UserServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        System.out.println(username+"\n"+pwd);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserService userService = (UserService) context.getBean("userService");
        boolean registe = userService.registe(username, pwd);
        PrintWriter pw = response.getWriter();
        pw.write("username=" + username + "\n");
        pw.write("pwd=" + pwd + "\n");
        pw.write("是否注册成功：" + registe);
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
