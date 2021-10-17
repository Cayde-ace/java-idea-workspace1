package com.ckr.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-09-22 22:54
 */
public class ComplexMail01 {
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
        message.setSubject("凯德六号", "utf-8");
        // 邮件的文本内容
//        message.setContent("<h2 style='color: darkviolet'>每个故事都有结局，这就是我的结局！</h2>", "text/html;charset=UTF-8");

        // ===================图片的发送实现===================
        MimeBodyPart image = new MimeBodyPart();
        // 图片数据处理
        DataHandler dataHandler = new DataHandler(new FileDataSource("D:\\workspace\\java_workspace\\idea-workspace1\\" +
                "javaweb-08-mail\\complex-mail\\src\\main\\image\\cayde 6.jpg"));
        image.setDataHandler(dataHandler);
        // 设置图片ID
        image.setContentID("cayde.jpg");

        // 准备正文内容，注意要添加图片的引用
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<h2 style='color: darkviolet'>这条音频请那位沉默寡言的厉害角色查收，黑桃A！</h2><img src='cid:cayde.jpg'",
                "text/html;charset=UTF-8");

        // 标注发送内容之间的关系
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(text);
        mimeMultipart.addBodyPart(image);
        mimeMultipart.setSubType("related");

        // 设置到message对象（表示一封电子邮件）中，保存修改
        message.setContent(mimeMultipart);
        message.saveChanges();

        // ===================图片的发送实现===================

        // 5.发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        // 6.关闭连接
        transport.close();
    }
}
