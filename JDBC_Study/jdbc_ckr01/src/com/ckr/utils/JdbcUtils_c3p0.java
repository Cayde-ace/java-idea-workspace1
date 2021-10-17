package com.ckr.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Shadowckr
 * @create 2021-08-28 19:55
 */

// 数据库连接工具类
/*

 */
public class JdbcUtils_c3p0 {

    private  static ComboPooledDataSource dataSource = null;

    static {

        try {

            //通过代码创建c3p0数据库连接池
//            dataSource = new ComboPooledDataSource();
//            dataSource.setDriverClass("com.mysql.jdbc.Driver");
//            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jdbc_study");
//            dataSource.setUser("root");
//            dataSource.setPassword("123456");
//            dataSource.setInitialPoolSize(10);
//            dataSource.setMinPoolSize(5);
//            dataSource.setMaxPoolSize(20);

            //通过读取c3p0的xml配置文件创建数据源，c3p0的xml配置文件c3p0-config.xml必须放在src目录下。
//            dataSource = new ComboPooledDataSource();//使用c3p0的默认配置来创建数据源
            dataSource = new ComboPooledDataSource("MySQL");//使用c3p0的命名配置来创建数据源

        } catch (Exception e) {
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
