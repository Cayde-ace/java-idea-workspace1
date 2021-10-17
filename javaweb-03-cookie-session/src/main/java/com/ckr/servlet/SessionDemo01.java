package com.ckr.servlet;

import com.ckr.pojo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-08 18:51
 */
public class SessionDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决乱码问题
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获得Session
        HttpSession session = req.getSession();
        // 给Session中存东西
        session.setAttribute("name",new Person("洋葱骑士", 35));
        // 获取Session的ID
        String id = session.getId();

        // 判断Session是不是新创建的
        if(session.isNew()){
            resp.getWriter().write("Session创建成功,ID:" + id);
        }else{
            resp.getWriter().write("Session已经在服务器中存在,ID:" + id);
        }

        // Session创建的时候做的事情：
//        Cookie cookie = new Cookie("JSESSIONID", id);
//        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
