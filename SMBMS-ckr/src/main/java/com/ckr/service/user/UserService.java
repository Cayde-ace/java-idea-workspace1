package com.ckr.service.user;

import com.ckr.pojo.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author Shadowckr
 * @create 2021-09-17 9:20
 */
public interface UserService {
    // 用户登录
    public User login(String userCode, String userPassword);

    // 根据用户的id修改密码
    public boolean updatePwd(int id, String userPassword);

    // 根据条件（用户的查询输入）查询用户记录的个数
    public int getUserCount(String queryUserName, int queryUserRole);

    // 根据条件（用户的查询输入）查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

    // 增加用户
    public boolean add(User user);

    // 根据查询的用户编码，判断用户是否存在（user为不为null）
    public User queryUserCodeExist(String userCode);

    // 根据用户的id查看用户信息
    public User getUserInfoById(String id);

    // 根据用户的id删除用户
    public boolean deleteUserById(int id);

    // 修改用户信息
    public boolean modify(User user);
}
