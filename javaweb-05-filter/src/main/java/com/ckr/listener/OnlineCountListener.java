package com.ckr.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Shadowckr
 * @create 2021-09-12 17:15
 */

// 统计网站的在线人数：统计Session（会话）个数
public class OnlineCountListener implements HttpSessionListener {

    // 创建session监听：监视你的一举一动
    // 一旦创建 session 就会触发一次这个事件！
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();

        System.out.println(se.getSession().getId());

        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");

        if(onlineCount == null){
            onlineCount = 1;
        }else{
            onlineCount = onlineCount + 1;
        }

        servletContext.setAttribute("OnlineCount", onlineCount);// 不断更新
    }

    // 销毁session监听
    // 一旦销毁 session 就会触发一次这个事件！
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();

        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");

        if(onlineCount == null){
            onlineCount = 0;
        }else{
            onlineCount = onlineCount - 1;
        }

        servletContext.setAttribute("OnlineCount", onlineCount);// 不断更新
    }

}

 /*
    Session销毁：
    1.手动销毁  getSession().invalidate();
    2.自动销毁
 */
