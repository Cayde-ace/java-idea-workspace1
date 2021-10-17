package com.ckr.service.role;

import com.ckr.dao.BaseDao;
import com.ckr.dao.role.RoleDao;
import com.ckr.dao.role.RoleDaoImplements;
import com.ckr.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Shadowckr
 * @create 2021-09-18 16:12
 */
public class RoleServiceImplements implements RoleService {
    // Service层会调用Dao层，所以要引入Dao层。
    private RoleDao roleDao;

    public RoleServiceImplements() {
        this.roleDao = new RoleDaoImplements();
    }

    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }
}
