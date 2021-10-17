package com.ckr.service.user;

import com.ckr.dao.BaseDao;
import com.ckr.dao.user.UserDao;
import com.ckr.dao.user.UserDaoImplements;
import com.ckr.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 业务层只处理对应的业务
 * Service层捕获异常，进行事务处理
 * 事务处理：调用Dao层的多个方法，必须使用同一个Connection对象（Connection对象作为参数传递）。
 * 事务完成之后，需要在Service层进行 Connection 对象的关闭，在Dao层关闭 PreparedStatement 对象和 ResultSet 对象。
 *
 * @author Shadowckr
 * @create 2021-09-17 9:23
 */
public class UserServiceImplements implements UserService {
    // Service层会调用Dao层，所以要引入Dao层。
    private UserDao userDao;

    public UserServiceImplements() {
        this.userDao = new UserDaoImplements();
    }

    @Override
    public User login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();

            // 通过Service层去调用Dao层对应的具体操作数据库的方法！
            user = userDao.getLoginUserInfo(connection, userCode, userPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    @Override
    public boolean updatePwd(int id, String userPassword) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            int i = userDao.updatePwd(connection, id, userPassword);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        Connection connection = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, queryUserName, queryUserRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userArrayList = null;

        System.out.println("queryUserName --- > " + queryUserName);
        System.out.println("queryUserRole --- > " + queryUserRole);
        System.out.println("currentPageNo --- > " + currentPageNo);
        System.out.println("pageSize --- > " + pageSize);

        try {
            connection = BaseDao.getConnection();
            userArrayList = userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userArrayList;
    }

    @Override
    public boolean add(User user) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);// 开启JDBC事务管理，关闭自动提交
            int updateRows = userDao.add(connection, user);
            connection.commit();// 提交事务

            if (updateRows > 0) {
                flag = true;
                System.out.println("Add Success!");
            } else {
                System.out.println("Add Failure!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                System.out.println("Rollback!");
                connection.rollback();// 失败就回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public User queryUserCodeExist(String userCode) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.queryUserCodeExist(connection, userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    @Override
    public User getUserInfoById(String id) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserInfoById(connection, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    @Override
    public boolean deleteUserById(int id) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            int i = userDao.deleteUserById(connection, id);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public boolean modify(User user) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int updateRows = userDao.modify(connection, user);
            connection.commit();

            if (updateRows > 0) {
                flag = true;
                System.out.println("修改用户信息成功！");
            } else {
                System.out.println("修改用户信息失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("修改失败，回滚事务！");
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

//    @Test
//    public void test() {
//        UserServiceImplements usi = new UserServiceImplements();
//        String userCode = "chuzihang";
//        String userPassword = "1234567";
//        User user = usi.login(userCode, userPassword);
//        System.out.println(user.getUserName() + ":" + user.getAddress());// 楚子航:美国西雅图
//    }

//    @Test
//    public void test() {
//        UserServiceImplements usi = new UserServiceImplements();
//        int id = 16;
//        String userPassword = "6666666";
//        boolean updatePwd = usi.updatePwd(id, userPassword);
//        System.out.println(updatePwd);// true
//    }

}
