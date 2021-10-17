<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-13
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1>登录</h1>

<form action="${pageContext.request.contextPath}/servlet/login" method="post">
    用户名：<label><input type="text" name="username"></label> <br>
    提交：<input type="submit">
</form>

</body>
</html>
