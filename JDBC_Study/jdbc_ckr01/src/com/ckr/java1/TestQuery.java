package com.ckr.java1;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Shadowckr
 * @create 2021-08-28 21:27
 */
public class TestQuery {

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // 获取一个数据库连接
            connection = JdbcUtils.getConnection();

            // 通过connection对象获取负责执行SQL命令的Statement对象
            statement = connection.createStatement();

            // 要执行的 SQL 命令
            String sql = "SELECT * FROM `users` WHERE `id` = 4";

            // 执行查询操作，executeQuery()方法返回存储查询结果的 ResultSet 对象
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.println("name=" + resultSet.getString("name"));
                System.out.println("email=" + resultSet.getString("email"));
                System.out.println("birthday=" + resultSet.getDate("birthday"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // SQL执行完成之后释放相关资源
            JdbcUtils.release(connection,statement,resultSet);
        }

    }

}
