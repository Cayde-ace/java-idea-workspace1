package com.ckr.servlet;

import com.ckr.pojo.User;
import com.ckr.utils.SendMail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-23 10:05
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 接收用户的请求参数
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");

            // 存入用户对象
            User user = new User(username, password, email);

            // 用户注册成功之后，给用户发送一封邮件。通过使用线程来专门发送邮件，防止出现耗时过久和网站注册人数过多的情况。
            SendMail sendMail = new SendMail(user);
            // 启动线程
            sendMail.start();

            // 提高用户体验
            // 引入多线程的目的是为了提高用户的体验，防止因发送邮件时间过长，导致前端响应过久，因此这里采用异步响应。
            req.setAttribute("message", "注册成功，我们已经发了一封带注册信息的电子邮件，" +
                    "请您查收！若网络不稳定，可能过会儿才能收到！");
            req.getRequestDispatcher("info.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "注册失败，请重试！");
            req.getRequestDispatcher("info.jsp").forward(req, resp);
        }
    }
}
