package com.ckr.java2;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Date;

/**
 * @author Shadowckr
 * @create 2021-08-31 12:12
 */
public class TestDelete {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = JdbcUtils.getConnection();

            // 要执行的 SQL 命令，SQL中的参数使用 ? 作为占位符
            String sql = "DELETE FROM `users` WHERE `id` = ?";

            // 通过connection对象获取负责执行 SQL 命令的 prepareStatement 对象
            preparedStatement = connection.prepareStatement(sql);

            // 为 SQL 语句中的参数赋值，注意，索引是从1开始
            preparedStatement.setInt(1,7);

            int i = preparedStatement.executeUpdate();

            if(i > 0){
                System.out.println("删除成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,preparedStatement,null);
        }

    }

}
