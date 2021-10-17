package com.ckr.java3;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shadowckr
 * @create 2021-08-31 15:16
 */

// 模拟转账成功时的业务场景
public class TestTransaction1 {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        ResultSet resultSet =null;

        try {

            connection = JdbcUtils.getConnection();

            // 通知数据库开启事务(start transaction)
            connection.setAutoCommit(false);

            String sql1 = "update `account` set `money` = `money`-100 where `name` = 'A'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();

            String sql2 = "update `account` set `money` = `money`+100 where `name` = 'B'";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            // 上面的两条 SQL 执行 Update 语句成功之后就通知数据库提交事务
            connection.commit();

            System.out.println("转账成功！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,preparedStatement,null);
        }

    }

}
