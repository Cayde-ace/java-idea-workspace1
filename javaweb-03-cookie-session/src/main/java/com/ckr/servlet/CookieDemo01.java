package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shadowckr
 * @create 2021-09-08 14:53
 */

// cookie:客户端技术 （响应，请求）
// 保存用户上一次访问的时间
public class CookieDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 服务器，告诉你，你来的时间。服务器把这个时间封装成为一个信件给你，你下次带着它来，服务器就知道你来了。
        // 解决中文乱码
        req.setCharacterEncoding("utf-8");
        // 设置response使用的编码集，控制response以"UTF-8"编码集向浏览器写入数据（必须写在方法的前面）
        resp.setCharacterEncoding("utf-8");
        // 要求浏览器以"UTF-8"编码集解码（打开服务器发送的数据） 　　
        resp.setHeader("content-type","text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        // Cookie，服务器端从客户端获取
        // 这里返回数组，说明Cookie可能存在多个
        Cookie[] cookies = req.getCookies();

        // 判断Cookie是否存在
        if(cookies != null){
            // 如果存在
            out.write("你上一次访问的时间是：");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                // 获取cookie的名字
                if(cookie.getName().equals("timeOfLastVisit")){
                    // 获取cookie中的值
                    long timeOfLastVisit = Long.parseLong(cookie.getValue());
                    Date date = new Date(timeOfLastVisit);
//                    out.write(String.valueOf(date));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String s = simpleDateFormat.format(date);
                    out.write(s);
                }
            }
        }else{
            out.write("这是您第一次访问本网站！");
        }

        // 服务器给客户端响应（发送）一个cookie
        Cookie cookie = new Cookie("timeOfLastVisit", System.currentTimeMillis() + "");
        // cookie有效期为 1 天
        cookie.setMaxAge(24*60*60);
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
