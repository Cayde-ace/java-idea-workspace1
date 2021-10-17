package com.ckr.utils;

import com.ckr.pojo.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-09-23 10:18
 */
public class SendMail extends Thread {
    // 用于给用户发送邮件的邮箱
    private final String from = "978090465@qq.com";
    // 邮箱的授权码
    private final String password = "gdbfatqtokhzbdhj";
    // 发送邮件的服务器地址
    private final String host = "smtp.qq.com";

    private User user;

    public SendMail(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", host);
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");
            // QQ邮箱需要设置SSL加密
            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

            // 使用JavaMail发送邮件
            // 1.首先创建整个应用程序的全局session对象
            // QQ的独特创建方式
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 温馨提示：登录第三方客户端时，密码框请输入“授权码”进行验证
                    return new PasswordAuthentication(from, password);
                }
            });

            // 开启session的debug模式，这样可以查看到程序发送Email的运行状态
            session.setDebug(true);
            // 2.通过session获得transport对象
            Transport transport = session.getTransport();
            // 3.使用邮箱的用户名和授权码连接邮件服务器
            transport.connect(host, from, password);
            // 4.创建邮件，需要传递session对象
            MimeMessage message = new MimeMessage(session);
            // 指明邮件的发送人
            message.setFrom(new InternetAddress(from));
            // 指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            // 邮件主题
            message.setSubject("用户注册通知", "utf-8");
            // 邮件的文本内容
            message.setContent("<h2 style='color: orange'>每个故事都有结局，这就是我的结局！</h2></br>" + "用户名："
                    + user.getUsername() + " 密码：" + user.getPassword() + "</br>" + "恭喜你成功注册！", "text/html;charset=UTF-8");

            // 5.发送邮件
            transport.sendMessage(message, message.getAllRecipients());

            // 6.关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
