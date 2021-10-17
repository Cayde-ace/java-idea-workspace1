package com.ckr.java1;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Shadowckr
 * @create 2021-08-28 21:10
 */
public class TestDelete {

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
            String sql = "DELETE FROM `users` WHERE `id` = 6";

            // 执行删除操作，executeUpdate()方法返回成功的条数
            int i = statement.executeUpdate(sql);
            if(i > 0){
                System.out.println("删除成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // SQL执行完成之后释放相关资源
            JdbcUtils.release(connection,statement,resultSet);
        }

    }

}

