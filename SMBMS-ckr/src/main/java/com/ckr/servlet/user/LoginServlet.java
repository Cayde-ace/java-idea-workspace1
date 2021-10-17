package com.ckr.servlet.user;

import com.ckr.pojo.User;
import com.ckr.service.user.UserService;
import com.ckr.service.user.UserServiceImplements;
import com.ckr.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet:控制层（Controller）1.接受用户的请求（参数）、2.调用业务层（返回数据）、3.视图跳转
 *
 * @author Shadowckr
 * @create 2021-09-17 10:44
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet Start!");

        // 获取登录页面（Login.jsp）传来的信息（用户名和密码）
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 调用Service层的方法（返回数据）
        UserService userService = new UserServiceImplements();
        User user = userService.login(userCode, userPassword);
        System.out.println("userCode:" + userCode + "\nuserPassword:" + userPassword);
        if (user != null) {
            // 将用户的信息放到 Session 中
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            // 重定向到主页（frame.jsp）
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            // 转发回登录页面，并通过EL表达式 ${error} 获取指定名称的属性值来提示用户：用户名或密码错误。
            req.setAttribute("error", "用户名或密码错误！每个故事都有结局，这就是我的结局！");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
