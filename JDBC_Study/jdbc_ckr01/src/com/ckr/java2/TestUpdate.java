package com.ckr.java2;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shadowckr
 * @create 2021-08-31 12:21
 */
public class TestUpdate {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = JdbcUtils.getConnection();

            // 要执行的 SQL 命令，SQL中的参数使用 ? 作为占位符
            String sql = "UPDATE `users` SET `birthday` = ? WHERE `id` = ?";

            // 通过connection对象获取负责执行 SQL 命令的 prepareStatement 对象
            preparedStatement = connection.prepareStatement(sql);

            // 为 SQL 语句中的参数赋值，注意，索引是从1开始
            String birthday = "2008-08-08";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(birthday);
            java.sql.Date date1 = new java.sql.Date(date.getTime());

            preparedStatement.setDate(1,date1);
            preparedStatement.setInt(2,6);

            int i = preparedStatement.executeUpdate();

            if(i > 0){
                System.out.println("更新成功！");
            }

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,preparedStatement,null);
        }

    }

}
