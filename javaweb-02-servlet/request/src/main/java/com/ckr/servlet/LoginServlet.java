package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Shadowckr
 * @create 2021-09-08 0:27
 */

/*
HttpServletRequest代表客户端的请求，用户通过Http协议访问服务器，HTTP请求中的所有信息都会被封装到 HttpServletRequest 中，
然后通过调用 HttpServletRequest 里的方法，获得客户端的所有信息。
 */

// 获取参数，请求转发
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决后台接收中文乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobbies = req.getParameterValues("hobbies");

        System.out.println("---------------");
        System.out.println(username);
        System.out.println(password);
        System.out.println(Arrays.toString(hobbies));
        System.out.println("---------------");

        System.out.println(req.getContextPath());// /cayde6
        // 通过请求转发
        // 这里的 / 代表当前的web应用程序 http://localhost:8080/cayde6
        req.getRequestDispatcher("/success.jsp").forward(req,resp);
    }
}
