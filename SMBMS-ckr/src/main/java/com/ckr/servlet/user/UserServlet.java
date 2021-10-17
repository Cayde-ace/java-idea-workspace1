package com.ckr.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.ckr.pojo.Role;
import com.ckr.pojo.User;
import com.ckr.service.role.RoleService;
import com.ckr.service.role.RoleServiceImplements;
import com.ckr.service.user.UserService;
import com.ckr.service.user.UserServiceImplements;
import com.ckr.utils.Constants;
import com.ckr.utils.PageSupport;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shadowckr
 * @create 2021-09-17 16:14
 */

// 实现Servlet复用，实现复用需要提取出方法，然后在doGet()函数或doPost()函数中调用。
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        System.out.println("method:" + method);

        if (method != null && method.equals("savepwd")) {
            this.updatePwd(req, resp);
        } else if (method != null && method.equals("pwdmodify")) {
            this.verifyOldPwd(req, resp);
        } else if (method != null && method.equals("query")) {
            this.query(req, resp);
        } else if (method != null && method.equals("add")) {
            this.add(req, resp);
        } else if (method != null && method.equals("getRoleList")) {
            this.getRoleList(req, resp);
        } else if (method != null && method.equals("userCodeExist")) {
            this.isUserCodeExist(req, resp);
        } else if (method != null && method.equals("view")) {
            this.getUserInfoById(req, resp);
        } else if (method != null && method.equals("deleteUser")) {
            this.deleteUserById(req, resp);
        } else if (method != null && method.equals("modify")) {
            this.getUserInfoByIdModify(req, resp);
        } else if (method != null && method.equals("executeModify")) {
            this.modify(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 更新（修改）密码
    private void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        // 通过Session获得用户的id
        Object user = req.getSession().getAttribute(Constants.USER_SESSION);

        String newpassword = req.getParameter("newpassword");
        boolean flag = false;
        // 判断用户是否存在以及新密码为不为空
        if (user != null && !StringUtils.isNullOrEmpty(newpassword)) {
            // 调用Service层的方法（返回数据）
            UserService userService = new UserServiceImplements();
            flag = userService.updatePwd(((User) user).getId(), newpassword);
            if (flag) {
                req.setAttribute(Constants.SYS_MESSAGE, "密码修改成功，请退出并使用新密码重新登录！");
                // 密码修改成功，要移除当前Session，用户重新登录
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute(Constants.SYS_MESSAGE, "密码修改失败，请重新输入！");
            }
        } else {
            req.setAttribute(Constants.SYS_MESSAGE, "新密码设置错误，请重新输入！");
        }

        try {
            // 转发回当前（pwdmodify.jsp）页面，并通过EL表达式 ${message} 获取指定名称的属性值来提示用户。
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    // 验证旧密码（Ajax验证旧密码实现：pwdmodify.js）
    private void verifyOldPwd(HttpServletRequest req, HttpServletResponse resp) {
        // 从Session中可以获得用户的旧密码，user包括了用户的所用信息
        Object user = req.getSession().getAttribute(Constants.USER_SESSION);

        String oldpassword = req.getParameter("oldpassword");
        Map<String, String> resultMap = new HashMap<>();

        if (user == null) {// 当前用户Session过期
            resultMap.put("result", "session_error");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {// 旧密码输入为空
            resultMap.put("result", "empty");
        } else {
            String userPassword = ((User) user).getUserPassword();
            if (oldpassword.equals(userPassword)) {// 旧密码输入正确
                resultMap.put("result", "true");
            } else {// 旧密码输入错误
                resultMap.put("result", "false");
            }
        }

        // ajax参数：dataType:将服务器端返回的数据转换成指定类型（dataType:"json"）
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 查询用户列表
    private void query(HttpServletRequest req, HttpServletResponse resp) {
        // 从前端获取请求数据（注意要和前端页面中的名字一一对应）
        String queryUserName = req.getParameter("queryUserName");
        String temp = req.getParameter("queryUserRole");// 值可能为null，用temp暂存
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;
        // queryUserRole为 0 相当于查询所有用户角色，因为在Dao层以下代码将不会执行。
        /*
        if (userRole > 0 && userRole < 4) {
                sql.append(" and u.`userRole` = ?");
                list.add(userRole);
            }
        */

        int pageSize = Constants.PAGE_SIZE;
        int currentPageNo = 1;

        // 前端传来的数据，若不符合查询sql语句，即如果用户不进行设置，值为null会影响sql查询，需要进行一些约束以及判断。
        if (queryUserName == null) {
            queryUserName = "";// StringUtils.isNullOrEmpty(queryUserName)返回true
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        UserService userService = new UserServiceImplements();
        RoleService roleService = new RoleServiceImplements();
        List<User> userList = null;
        List<Role> roleList = null;

        // 调用Service层的方法（返回数据）
        // 根据条件（用户的查询输入）查询用户记录的个数
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        //
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        // 总页数
        int totalPageCount = pageSupport.getTotalPageCount();
        // 防止用户输入的页数小于1或者大于总页数
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }
        // 获取用户列表和角色列表
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        roleList = roleService.getRoleList();

        // 将数据存入request请求中，并将此请求转发回前端
        req.setAttribute("userList", userList);
        req.setAttribute("roleList", roleList);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    // 增加用户
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("Adding User!");
        // 从前端获取请求参数（name="xxxXxx"），即用户输入的值，一共8个
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        // 设置用户属性，一共10个
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        // 调用Service层的方法（返回数据）
        UserService userService = new UserServiceImplements();
        boolean flag = userService.add(user);
        // 如果添加成功，则页面重定向到用户管理页面（http://localhost:8080/smbms/jsp/user.do?method=query）
        // 否则重新刷新，再次跳转到当前页面
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    // 获取用户角色列表（Ajax获取下拉列表框用户角色名称）
    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImplements();
        roleList = roleService.getRoleList();
        // 将roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(roleList));
        writer.flush();
        writer.close();
    }

    // 查询当前用户编码（登录时的用户名）是否存在（Ajax后台验证userCode是否已存在）
    private void isUserCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取用户输入的用户编码
        String userCode = req.getParameter("userCode");

        HashMap<String, String> resultMap = new HashMap<>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            // userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        } else {
            // 如果用户输入的用户编码不为null和empty，则查询是否存在这个用户与之冲突
            // 调用Service层的方法（返回数据）
            UserService userService = new UserServiceImplements();
            User user = userService.queryUserCodeExist(userCode);
            if (user != null) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "notExist");
            }
        }
        // 将resultMap转换为json字符串，通过json的格式输出
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    // 根据用户的id查看用户信息 method.equals("view")
    private void getUserInfoById(HttpServletRequest req, HttpServletResponse resp) {
        // 获取要查看用户的id
        String id = req.getParameter("uid");// userlist.js
        System.out.println("id = " + id);
        if (!StringUtils.isNullOrEmpty(id)) {
            // 调用Service层的方法（返回数据）
//            System.out.println("id = " + id);
            UserService userService = new UserServiceImplements();
            User user = userService.getUserInfoById(id);
            req.setAttribute("user", user);
            try {
                req.getRequestDispatcher("userview.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据用户的id删除用户
    private void deleteUserById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取要删除用户的id
        String id = req.getParameter("uid");
        System.out.println("id = " + id);
        int deleteId = 0;
        try {
            deleteId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            deleteId = 0;
        }
        Map<String, String> resultMap = new HashMap<>();
        if (deleteId <= 0) {
            resultMap.put("deleteResult", "notExist");
        } else {
            UserService userService = new UserServiceImplements();
            boolean flag = userService.deleteUserById(deleteId);
            if (flag) {
                resultMap.put("deleteResult", "true");
            } else {
                resultMap.put("deleteResult", "false");
            }
        }
        // 将resultMap转换成json字符串传给Ajax
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    // 根据用户的id查看用户信息 method.equals("modify")
    private void getUserInfoByIdModify(HttpServletRequest req, HttpServletResponse resp) {
        // 获取要查看用户的id
        String id = req.getParameter("uid");// userlist.js
        System.out.println("id = " + id);
        if (!StringUtils.isNullOrEmpty(id)) {
            // 调用Service层的方法（返回数据）
//            System.out.println("id = " + id);
            UserService userService = new UserServiceImplements();
            User user = userService.getUserInfoById(id);
            req.setAttribute("user", user);
            try {
                req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 修改用户信息
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 获取前端请求参数
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        // 创建一个user对象并设置这些属性
        User user = new User();

        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        // 调用Service层的方法（返回数据）
        UserService userService = new UserServiceImplements();
        boolean flag = userService.modify(user);
        // 判断是否修改成功来决定跳转或重定向到哪个页面
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }
    }
}
