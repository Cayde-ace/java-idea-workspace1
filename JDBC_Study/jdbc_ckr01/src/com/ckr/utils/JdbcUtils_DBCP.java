package com.ckr.utils;

//import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-08-28 19:55
 */

// 数据库连接工具类
/*
javax,x:extension 扩展
在java中，编写数据库连接池需实现 java.sql.DataSource 接口，每一种数据库连接池都是 DataSource 接口的实现。
DBCP连接池就是 java.sql.DataSource 接口的一个具体实现。
 */
public class JdbcUtils_DBCP {

    private  static DataSource dataSource = null;

    static {

        try {
            // 读取 dbcp-config.properties 配置文件中的数据库连接信息
            InputStream inputStream = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcp-config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            // 创建数据源 工厂模式
            try {
                dataSource = BasicDataSourceFactory.createDataSource(properties);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 从数据源中获取数据库连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
