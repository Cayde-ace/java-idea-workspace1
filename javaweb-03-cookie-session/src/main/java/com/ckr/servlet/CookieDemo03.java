package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Shadowckr
 * @create 2021-09-08 16:18
 */

// 中文数据传递
public class CookieDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");

        Cookie[] cookies = req.getCookies();

        PrintWriter out = resp.getWriter();

        if (cookies!=null){
            // 如果存在
            out.write("你的名字：");

            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("name")){
                    // 解码
                    out.write(URLDecoder.decode(cookie.getValue(),"UTF-8"));
                }
            }
        }else {
            out.write("这是您第一次访问本站！");
        }

        // 编码
        Cookie cookie = new Cookie("name", URLEncoder.encode("凯德六号", "UTF-8"));
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
