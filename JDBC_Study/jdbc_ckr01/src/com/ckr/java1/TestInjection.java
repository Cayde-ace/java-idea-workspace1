package com.ckr.java1;

import com.ckr.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
SQL注入问题：通过巧妙的技巧来拼接字符串，造成 SQL 短路，从而获取数据库数据。

SQL注入即是指web应用程序对用户输入数据的合法性没有判断或过滤不严，
攻击者可以在web应用程序中事先定义好的查询语句的结尾上添加额外的SQL语句，
在管理员不知情的情况下实现非法操作，以此来实现欺骗数据库服务器执行非授权的任意查询，从而进一步得到相应的数据信息。
 */

/**
 * @author Shadowckr
 * @create 2021-08-28 21:43
 */
public class TestInjection {

    public static void main(String[] args) {

//        login("Cayde 6","123456"); // 正常登录
//        login("'' or '1=1'","123456"); // '1=1'' AND `password` = '123456''
        login("'or '1=1","123456"); // SQL 注入 获取了表的全部数据！

    }

    public static void login(String username,String password){

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = JdbcUtils.getConnection();

            statement = connection.createStatement();

            String sql = "select * from `users` where `name` = '" + username + "' AND `password` = '" + password + "'";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.println("username=" + resultSet.getString("name"));
                System.out.println("password=" + resultSet.getString("password"));
                System.out.println("birthday=" + resultSet.getDate("birthday"));
                System.out.println("----------Ace!----------");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }

    }

}
