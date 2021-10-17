package com.ckr.java2;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
//import java.sql.Date;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Shadowckr
 * @create 2021-08-31 11:23
 */
public class TestInsert {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = JdbcUtils.getConnection();

            // 要执行的 SQL 命令，SQL中的参数使用 ? 作为占位符
            String sql = "INSERT INTO `users`(`id`,`name`,`password`,`email`,`birthday`) VALUES" + "(?,?,?,?,?)";

            // 通过connection对象获取负责执行 SQL 命令的 prepareStatement 对象
            preparedStatement = connection.prepareStatement(sql);

            // 为 SQL 语句中的参数赋值，注意，索引是从1开始
            preparedStatement.setInt(1,7);
            preparedStatement.setString(2,"Adriana");
            preparedStatement.setString(3,"123456");
            preparedStatement.setString(4,"adriana@sina.com");
            preparedStatement.setDate(5,new java.sql.Date(new Date().getTime()));

            int i = preparedStatement.executeUpdate();

            if(i > 0){
                System.out.println("插入成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,preparedStatement,null);
        }

    }

}
