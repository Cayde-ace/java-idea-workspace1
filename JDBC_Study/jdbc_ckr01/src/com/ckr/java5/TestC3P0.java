package com.ckr.java5;

import com.ckr.utils.JdbcUtils_c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
//import java.sql.Date;

/**
 * @author Shadowckr
 * @create 2021-08-31 11:23
 */
public class TestC3P0 {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = JdbcUtils_c3p0.getConnection();

            // 要执行的 SQL 命令，SQL中的参数使用 ? 作为占位符
            String sql = "INSERT INTO `users`(`id`,`name`,`password`,`email`,`birthday`) VALUES" + "(?,?,?,?,?)";

            // 通过connection对象获取负责执行 SQL 命令的 prepareStatement 对象
            preparedStatement = connection.prepareStatement(sql);

            // 为 SQL 语句中的参数赋值，注意，索引是从1开始
            preparedStatement.setInt(1,9);
            preparedStatement.setString(2,"Shadow");
            preparedStatement.setString(3,"123456");
            preparedStatement.setString(4,"shadow@sina.com");
            preparedStatement.setDate(5,new java.sql.Date(new Date().getTime()));

            int i = preparedStatement.executeUpdate();

            if(i > 0){
                System.out.println("插入成功(c3p0)！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils_c3p0.release(connection,preparedStatement,null);
        }

    }

}
