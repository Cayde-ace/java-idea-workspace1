<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-8
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<h1>欢迎来到战网游戏平台，请登录：</h1>

<div>
    <%-- 这里表单表示的意思：以post方式提交表单，提交这个login(登录)请求到后台处理->LoginServlet --%>
    <form action="${pageContext.request.contextPath}/login" method="post">

        用户名：<input type="text" name="username"> <br>
        密&nbsp;&nbsp;&nbsp;码： <input type="password" name="password"> <br>
        你喜欢的游戏：
        <input type="checkbox" name="hobbies" value="巫师三">巫师三
        <input type="checkbox" name="hobbies" value="刺客信条">刺客信条
        <input type="checkbox" name="hobbies" value="生化危机">生化危机
        <input type="checkbox" name="hobbies" value="神界原罪2">神界原罪2
        <input type="checkbox" name="hobbies" value="黑暗之魂3">黑暗之魂3
        <br>
        <input type="submit">
    </form>
</div>

</body>
</html>
