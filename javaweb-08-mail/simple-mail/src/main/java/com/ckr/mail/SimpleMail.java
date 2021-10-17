package com.ckr.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-09-22 21:59
 */

/*
QQ邮箱的POP3与SMTP服务器是什么？
QQ邮箱 POP3 和 SMTP 服务器地址设置如下：
邮箱	POP3服务器（端口995）	SMTP服务器（端口465或587）
qq.com	pop.qq.com	            smtp.qq.com
SMTP服务器需要身份验证。
 */
public class SimpleMail {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        Properties properties = new Properties();
        // 设置QQ邮件服务器
        properties.setProperty("mail.host", "smtp.qq.com");
        // 邮件发送协议
        properties.setProperty("mail.transport.protocol", "smtp");
        // 需要验证用户名与密码
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
                return new PasswordAuthentication("978090465@qq.com", "mipqlysgihydbcbb");
            }
        });

        // 开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(true);

        // 2.通过session获得transport对象
        Transport transport = session.getTransport();

        // 3.使用邮箱的用户名和授权码连接邮件服务器
        transport.connect("smtp.qq.com", "978090465@qq.com", "mipqlysgihydbcbb");

        // 4.创建邮件，需要传递session对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发送人
        message.setFrom(new InternetAddress("978090465@qq.com"));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("978090465@qq.com"));
        // 邮件主题
        message.setSubject("盖牌认输永远不在选择之列", "utf-8");
        // 邮件的文本内容
        message.setContent("<h2 style='color: violet'>每个故事都有结局，这就是我的结局！</h2>", "text/html;charset=UTF-8");

        // 5.发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        // 6.关闭连接
        transport.close();
    }
}
