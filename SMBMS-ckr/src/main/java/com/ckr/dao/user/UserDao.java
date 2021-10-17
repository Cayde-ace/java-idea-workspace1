package com.ckr.dao.user;

import com.ckr.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Shadowckr
 * @create 2021-09-16 22:04
 */
public interface UserDao {
    // 获取登录用户的信息
    public User getLoginUserInfo(Connection connection, String userCode, String userPassword) throws SQLException;

    // 修改当前用户密码
    // 增删改都会影响数据库的变化，并且执行sql返回int类型，表明有几行受到了影响
    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    // 根据用户输入的用户名称和用户角色（id）来查询用户的数量（多少条用户记录），默认查询用户总数（所有用户记录的个数）
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException;

    // 根据条件（用户的输入）查询用户列表
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException;

    // 增加用户
    public int add(Connection connection, User user) throws SQLException;

    // 根据查询的用户编码，判断用户是否存在（user为不为null）
    public User queryUserCodeExist(Connection connection, String userCode) throws SQLException;

    // 根据用户的id查看用户信息
    public User getUserInfoById(Connection connection, String id) throws SQLException;

    // 根据用户的id删除用户
    public int deleteUserById(Connection connection, int id) throws SQLException;

    // 修改用户信息
    public int modify(Connection connection, User user) throws SQLException;
}
