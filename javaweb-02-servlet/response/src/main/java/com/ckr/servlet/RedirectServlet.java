package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-07 19:40
 */

// 一个web资源(B)收到客户端(A)的请求后，web资源(B)会通知客户端(A)去访问另外一个web资源(C)，这个过程就叫重定向。
// 常见场景：用户登录
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setHeader("Location","/ckr/img");
//        // int SC_MOVED_TEMPORARILY = 302;
//        resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);

        // 与上述等同（重定向） http://localhost:8080
        resp.sendRedirect("/ckr/img");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
