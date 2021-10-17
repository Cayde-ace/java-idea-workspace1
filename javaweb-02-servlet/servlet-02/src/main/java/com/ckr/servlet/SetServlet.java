package com.ckr.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-06 18:14
 */
public class SetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // web容器在启动的时候，它会为每个 web 程序都创建一个对应的 ServletContext 对象，它代表了当前的 web 应用程序。
        ServletContext servletContext = this.getServletContext();

        String username = "凯德六号";// 数据
        // 将一个数据保存在了 ServletContext 中，名字：username 值：username("凯德六号")
        servletContext.setAttribute("username",username);

        resp.getWriter().print("Save the name!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
