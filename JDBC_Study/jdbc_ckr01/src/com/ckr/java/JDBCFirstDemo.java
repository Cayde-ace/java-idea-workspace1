package com.ckr.java;

import java.sql.*;

/**
 * @author Shadowckr
 * @create 2021-08-28 14:46
 */
public class JDBCFirstDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //要连接的数据库 URL
        String url = "jdbc:mysql://localhost:3306/jdbc_study?useUnicode=true&characterEncoding=utf8&useSSL=true";
        //连接数据库时使用的用户名
        String username = "root";
        //连接数据库时使用的密码
        String password = "123456";

        //1.加载驱动
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());不推荐使用这种方式来加载驱动
        //推荐使用这种方式来加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2.获取与数据库的连接
        Connection connection = DriverManager.getConnection(url, username, password);

        //3.获取用于向数据库发送sql语句的statement
        Statement statement = connection.createStatement();

        String sql = "select `id`,`name`,`password`,`email`,`birthday` from `users`";

        //4.向数据库发送sql,并获取代表结果集的resultset
        ResultSet resultSet = statement.executeQuery(sql);

        //5.取出结果集的数据
        while(resultSet.next()){
            System.out.println("id=" + resultSet.getObject("id"));
            System.out.println("name=" + resultSet.getObject("name"));
            System.out.println("password=" + resultSet.getObject("password"));
            System.out.println("email=" + resultSet.getObject("email"));
            System.out.println("birthday=" + resultSet.getObject("birthday"));

        }

        //6.关闭连接，释放资源
        resultSet.close();
        statement.close();
        connection.close();

    }
}
