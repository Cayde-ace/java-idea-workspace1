package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-09-07 10:40
 */

/*
在 java 目录下新建 properties 配置文件
在 resources 目录下新建 properties 配置文件
发现：上述二者都被打包到了同一个路径下：classes，我们俗称这个路径为 classpath。

在 Artifacts 中配置输出目录

"/WEB-INF/classes/com/ckr/servlet/db1.properties"
"/WEB-INF/classes/db.properties"
 */

// 读取资源文件
public class ServletDemo04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream resourceAsStream = this.getServletContext().getResourceAsStream(
                "/WEB-INF/classes/com/ckr/servlet/db1.properties");

        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        resp.getWriter().print(username + ":" + password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
