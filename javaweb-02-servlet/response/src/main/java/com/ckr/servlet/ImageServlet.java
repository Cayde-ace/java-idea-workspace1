package com.ckr.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Shadowckr
 * @create 2021-09-07 17:43
 */

// HttpServletResponse 验证码功能：后端实现，需要用到 Java 的图片类，生成一个图片
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 让浏览器3秒自动刷新一次
        resp.setHeader("refresh","3");

        // 在内存中创建一个图片
        BufferedImage bufferedImage = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);

        // 获得图片（笔）
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();

        // 设置图片的背景颜色
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,80,20);

        // 给图片写数据
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(new Font(null,Font.BOLD,20));
        graphics2D.drawString(GenerateRandomNumber(),0,20);

        // 告诉浏览器，这个请求用图片的方式打开
        resp.setContentType("image/jpeg");

        // 网站存在缓存，不让浏览器缓存
        resp.setDateHeader("expires",-1);
        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");

        // 把图片写给浏览器
        ImageIO.write(bufferedImage,"jpg",resp.getOutputStream());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 生成随机数
    private String GenerateRandomNumber(){
        Random random = new Random();
        String num = random.nextInt(6666666) + "";
        StringBuffer stringBuffer;
        stringBuffer = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++) {
            stringBuffer.append("0");
        }
        num = num + stringBuffer.toString();
        return num;
    }

}
