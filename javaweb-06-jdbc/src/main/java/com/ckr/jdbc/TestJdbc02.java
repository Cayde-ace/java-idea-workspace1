package com.ckr.jdbc;

import java.sql.*;

/**
 * @author Shadowckr
 * @create 2021-09-13 16:41
 */
public class TestJdbc02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 配置信息
        String url = "jdbc:mysql://localhost:3306/jdbc_study?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO `users`(`id`,`name`,`password`,`email`,`birthday`) VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 10);
        preparedStatement.setString(2,"Max");
        preparedStatement.setString(3, "123456");
        preparedStatement.setString(4, "max@sina.com");
        preparedStatement.setDate(5, new java.sql.Date(new java.util.Date().getTime()));

        int i = preparedStatement.executeUpdate();

        if(i > 0){
            System.out.println("插入成功！");
        }

        // 关闭连接，释放资源（一定要做），先开后关。
        preparedStatement.close();
        connection.close();
    }
}
