package com.ckr.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Shadowckr
 * @create 2021-09-16 16:52
 */

// 操作数据库的公共类（定义了操作数据库中的表的通用操作）
// DAO:data(base) access object 数据访问对象
public class BaseDao {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    // 静态代码块，类加载的时候初始化
    static {
        Properties properties = new Properties();

        // 通过类加载器读取对应的资源
        InputStream resourceAsStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    // 获取数据库的连接对象
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 查询公共方法
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement,
                                    ResultSet resultSet, String sql, Object[] params) throws SQLException {
        // 预编译的sql，在后面直接执行
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            // 占位符从1开始，数组下标从0开始！
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    // 增加、删除、修改公共方法
    public static int execute(Connection connection, PreparedStatement preparedStatement,
                              String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        int updateRows = 0;
        updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    // 释放资源
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement,
                                        ResultSet resultSet) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                // GC回收
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }
}
