package com.ckr.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Shadowckr
 * @create 2021-09-07 15:29
 */

// HttpServletResponse 下载文件
public class FileDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1. 获取下载文件的路径
//        String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/.ycqs.jpg");
//        String realPath = "D:\\development_tools\\apache-tomcat-9.0.52\\webapps\\ckr\\WEB-INF\\classes\\.ycqs.jpg";
        String realPath = "D:\\workspace\\java_workspace\\idea-workspace1\\javaweb-02-servlet\\response\\target\\response\\WEB-INF\\classes\\ycqs.jpg";
//        String realPath = "D:\\workspace\\java_workspace\\idea-workspace1\\javaweb-02-servlet\\response\\target\\classes\\ycqs.jpg";
        System.out.println("下载文件的路径：" + realPath);
//        2. 获取下载的文件名
        String filename = realPath.substring(realPath.lastIndexOf("\\") + 1);
//        3. 让浏览器能够支持下载我们需要的东西(Content-Disposition)，中文文件名用 URLEncoder.encode 编码，否则有可能乱码
        resp.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
//        4. 获取下载文件的输入流
        FileInputStream fileInputStream = new FileInputStream(realPath);
//        5. 创建缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
//        6. 获取OutputStream输出流对象
        ServletOutputStream outputStream = resp.getOutputStream();
//        7. 将FileInputStream流写入到 buffer 缓冲区，并使用ServletOutputStream流将缓冲区中的数据输出到客户端！
        while((len = fileInputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }

        //正常用 try-catch-finally
        outputStream.close();
        fileInputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

/*
D:\development_tools\apache-tomcat-9.0.52\webapps\ckr\WEB-INF\classes
D:\development_tools\apache-tomcat-9.0.52\webapps\ckr\WEB-INF\classes\.ycqs.jpg
D:\workspace\java_workspace\idea-workspace1\javaweb-02-servlet\response\target\classes\ycqs.jpg
D:\workspace\java_workspace\idea-workspace1\javaweb-02-servlet\response\target\response\WEB-INF\classes\ycqs.jpg
target\response\WEB-INF\classes\ycqs.jpg
 */
