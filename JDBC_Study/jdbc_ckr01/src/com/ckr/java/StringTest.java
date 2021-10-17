package com.ckr.java;

/**
 * @author Shadowckr
 * @create 2021-08-28 16:51
 */
public class StringTest {

    public static void main(String[] args) {
        String username = "Alex";
        String password = "123456";

        System.out.println("username");

        System.out.println(username);

        System.out.println("'" + username + "'");// 'Alex'

        System.out.println("1" + username + "1");

        String sql1 = "select * from `users` where `name` = " + username + " AND " + "where `password` = " + password;

        System.out.println(sql1);
        //select * from `users` where `name` = Alex AND where `password` = 123456

        //在数据库中，字符串是用一对单引号括起来的。'Alex' '123456'

        String sql2 = "select * from `users` where `name` = '" + username + "' AND where `password` = '" + password + "'";

        System.out.println(sql2);

        // correct: no where
        String sql3 = "select * from `users` where `name` = '" + username + "' AND `password` = '" + password + "'";

        System.out.println(sql3);

    }
}
