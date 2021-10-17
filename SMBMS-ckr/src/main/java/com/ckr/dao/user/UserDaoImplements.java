package com.ckr.dao.user;

import com.ckr.dao.BaseDao;
import com.ckr.pojo.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao层抛出异常，让Service层去捕获处理
 *
 * @author Shadowckr
 * @create 2021-09-16 22:17
 */
public class UserDaoImplements implements UserDao {
    @Override
    public User getLoginUserInfo(Connection connection, String userCode, String userPassword) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from `smbms_user` where `userCode` = ?";

            Object[] params = {userCode};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);

            // 匹配密码
            if (user != null) {
                if (!(user.getUserPassword().equals(userPassword))) {
                    user = null;
                }
            }
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "update `smbms_user` set `userPassword` = ? where `id` = ?";

            Object[] params = {password, id};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);

            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();

            // user表和role表连接查询，等值连接(与内连接一样)，默认查询用户总数（所有用户记录的个数）
            sql.append("select count(1) as count from `smbms_user` u,`smbms_role` r where u.`userRole` = r.`id`");
            // arraylist存放用来替代 ? 的参数
            ArrayList<Object> arrayList = new ArrayList<>();

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.`userName` like ?");
                // 模糊查询
                arrayList.add("%" + userName + "%");// index:0
            }
            if (userRole > 0 && userRole < 4) {
                sql.append(" and u.`userRole` = ?");
                arrayList.add(userRole);// index:1
            }

            //将arrayList转换为数组
            Object[] params = arrayList.toArray();
            System.out.println("sql:" + sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<User> userArrayList = new ArrayList<>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.`roleName` as `userRoleName` from `smbms_user` u,`smbms_role` r " +
                    "where u.`userRole` = r.`id`");
            List<Object> list = new ArrayList<>();

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.`userName` like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0 && userRole < 4) {
                sql.append(" and u.`userRole` = ?");
                list.add(userRole);
            }

            // 实现分页显示
            // 第N页: limit (pageNo-1) * pageSize,pageSize
            // [pageNo:页码 pageSize:单页面显示条数]
            // currentPageNo:当前页面页码
            sql.append(" order by `creationDate` DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql:" + sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
                userArrayList.add(user);
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return userArrayList;
    }

    @Override
    public int add(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "insert into `smbms_user` (userCode, userName, userPassword, gender, birthday, phone, address,\n" +
                    " userRole, createdBy, creationDate) values (?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getUserRole(), user.getCreatedBy(), user.getCreationDate()};

            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

    @Override
    public User queryUserCodeExist(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from `smbms_user` where `userCode` = ?";

            Object[] params = {userCode};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public User getUserInfoById(Connection connection, String id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (connection != null) {
            String sql = "select u.*,r.roleName as userRoleName from `smbms_user` u, `smbms_role` r " +
                    "where u.userRole = r.id and u.id = ?";

            Object[] params = {id};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();
                // 设置一共14个属性
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public int deleteUserById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "delete from `smbms_user` where `id` = ?";

            Object[] params = {id};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

    @Override
    public int modify(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "update `smbms_user` set userName = ?,gender = ?,birthday = ?,phone = ?,address = ?,\n" +
                    "userRole = ?,modifyBy = ?,modifyDate = ? where id = ?";

            Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(), user.getPhone(),
                    user.getAddress(), user.getUserRole(), user.getModifyBy(), user.getModifyDate(), user.getId()};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

}
