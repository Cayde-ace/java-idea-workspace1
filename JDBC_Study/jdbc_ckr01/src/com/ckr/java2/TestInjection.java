package com.ckr.java2;

import com.ckr.utils.JdbcUtils;

import java.sql.*;

/**
 * @author Shadowckr
 * @create 2021-08-31 12:51
 */

/*
PreparedStatement防止 SQL 注入的本质：执行的时候参数会用引号包起来，
并把参数中的引号作为转义字符，从而避免了参数也作为条件的一部分。
 */

public class TestInjection {

    public static void main(String[] args) {

//        login("Cayde 6","123456"); // 正常登录
//        login("'' or '1=1'","123456"); // '1=1'' AND `password` = '123456''
//        login("'or '1=1","123456"); // SQL 注入 获取了表的全部数据！
        login("'' or 1=1","123456");

    }

    public static void login(String username,String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtils.getConnection();

            String sql = "SELECT * FROM `users` WHERE `name` = ? AND `password` = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println("username=" + resultSet.getString("name"));
                System.out.println("password=" + resultSet.getString("password"));
                System.out.println("birthday=" + resultSet.getDate("birthday"));
                System.out.println("----------Ace!----------");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }

    }

}
