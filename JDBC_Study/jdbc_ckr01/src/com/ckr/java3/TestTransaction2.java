package com.ckr.java3;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import java.sql.ResultSet;

/**
 * @author Shadowckr
 * @create 2021-08-31 15:16
 */

// 模拟转账过程中出现异常导致有一部分 SQL 执行失败后让数据库自动回滚事务
public class TestTransaction2 {

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

            // 用这行代码模拟执行完 sql1 之后程序出现了异常而导致后面的 sql2 无法正常执行。
            // 事务因此也无法正常提交，此时数据库会自动执行回滚操作。
//            int x = 1/0;

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
