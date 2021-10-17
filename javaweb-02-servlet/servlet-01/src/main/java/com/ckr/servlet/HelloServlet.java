package com.ckr.servlet;

import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Shadowckr
 * @create 2021-09-06 11:43
 */
public class HelloServlet extends HttpServlet {
    // alt + shift + s
    // 由于 get 或者 post 只是请求实现的不同的方式，可以相互调用，业务逻辑都一样。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//        ServletOutputStream outputStream = resp.getOutputStream();// 字节流
        PrintWriter writer = resp.getWriter();// 字符流
        writer.print("Hello,Servlet! My name is Cayde 6!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        doGet(req,resp);
    }
}
