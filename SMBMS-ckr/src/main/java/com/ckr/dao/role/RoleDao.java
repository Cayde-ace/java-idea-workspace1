package com.ckr.dao.role;

import com.ckr.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Shadowckr
 * @create 2021-09-18 15:54
 */
public interface RoleDao {
    // 获取角色列表
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
