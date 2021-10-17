package com.ckr.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Shadowckr
 * @create 2021-09-13 17:47
 */
public class TestJdbc03 {

    @Test
    public void test(){
        // 配置信息
        String url = "jdbc:mysql://localhost:3306/jdbc_study?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);

            // 通知数据库开启事务(start transaction)
            connection.setAutoCommit(false);

            String sql1 = "update `account` set `money` = `money`-100 where `name` = 'C'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();

            // 制造错误
//            int i = 1/0;

            String sql2 = "update `account` set `money` = `money`+100 where `name` = 'D'";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            // 上面的两条 SQL 执行 Update 语句成功之后就通知数据库提交事务
            connection.commit();

            System.out.println("转账成功！Success!");

        } catch (Exception e) {
            if(connection != null){
                try {
                    // 捕获到异常之后手动通知数据库执行回滚事务的操作
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }
}
