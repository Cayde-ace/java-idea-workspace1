package com.ckr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-08-28 19:55
 */
public class JdbcUtils {

    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {

        try {
            // 读取 db.properties 配置文件中的数据库连接信息
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");

            Properties properties = new Properties();

            properties.load(inputStream);

            // 获取数据库连接驱动
            String driver = properties.getProperty("driver");
            // 获取数据库连接 URL 地址
            url = properties.getProperty("url");
            // 获取数据库连接用户名
            username = properties.getProperty("username");
            // 获取数据库连接密码
            password = properties.getProperty("password");

            try {
                // 加载数据库驱动
                Class.forName(driver);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 获取数据库连接对象
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // 释放资源，要释放的资源包括 Connection 数据库连接对象，负责执行SQL命令的 Statement 对象，存储查询结果的 ResultSet 对象
    public static void release(Connection connection, Statement statement, ResultSet resultSet){

        if(resultSet != null){
            try {
                // 关闭存储查询结果的 ResultSet 对象
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(statement != null){
            try {
                // 关闭负责执行SQL命令的 Statement 对象
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(connection != null){
            try {
                // 关闭 Connection 数据库连接对象
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
