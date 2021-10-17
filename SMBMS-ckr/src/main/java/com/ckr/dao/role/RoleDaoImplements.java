package com.ckr.dao.role;

import com.ckr.dao.BaseDao;
import com.ckr.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shadowckr
 * @create 2021-09-18 15:55
 */
public class RoleDaoImplements implements RoleDao {
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Role> roleArrayList = new ArrayList<>();

        if (connection != null) {
            String sql = "select * from `smbms_role`";
            Object[] params = {};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));
                roleArrayList.add(role);
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return roleArrayList;
    }
}
