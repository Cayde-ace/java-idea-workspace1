package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-07 20:05
 */
public class RequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 处理请求
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println(username + ":" + password);

        // 重定向的时候一定要注意：路径问题，否则404
        // 如果传递给response.sendRedirect()方法的相对URL以“/”开头，它是相对于整个WEB站点的根目录 http://localhost:8080
        resp.sendRedirect("/ckr/success.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
