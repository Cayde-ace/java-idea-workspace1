package com.ckr.servlet;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-06 20:42
 */

// 请求转发（不同于重定向）
public class ServletDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Welcome to ServletDemo02!");

        ServletContext servletContext = this.getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/sd1");// 转发的请求路径
//        requestDispatcher.forward(req,resp);// 调用 forward(req,resp) 实现请求转发
        servletContext.getRequestDispatcher("/sd1").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
