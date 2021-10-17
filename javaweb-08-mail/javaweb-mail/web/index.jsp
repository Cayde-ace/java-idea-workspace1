<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-23
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/RegisterServlet.do" method="post">
    用户名：<p><label><input type="text" name="username"></label></p>
    密码：<p><label><input type="password" name="password"></label></p>
    邮箱：<p><label><input type="text" name="email"></label></p>
    <input type="submit" value="注册">
</form>

</body>
</html>
