package com.ckr.jdbc;

import java.sql.*;

/**
 * @author Shadowckr
 * @create 2021-09-13 15:54
 */
public class TestJdbc01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 配置信息
        String url = "jdbc:mysql://localhost:3306/jdbc_study?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM `users` WHERE `id` = 4";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            System.out.println("id=" + resultSet.getInt("id"));
            System.out.println("name=" + resultSet.getString("name"));
            System.out.println("password=" + resultSet.getString("password"));
            System.out.println("email=" + resultSet.getString("email"));
            System.out.println("birthday=" + resultSet.getDate("birthday"));
        }

        // 关闭连接，释放资源（一定要做），先开后关。
        resultSet.close();
        statement.close();
        connection.close();
    }
}
