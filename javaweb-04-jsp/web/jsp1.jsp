<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 0:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--定制错误页面--%>
<%@page errorPage="error/500.jsp" %>

<%--显示的声明这是一个错误页面--%>
<%@page isErrorPage="true" %>
<%@page pageEncoding="utf-8" %>

<html>
<head>
    <title>WrongPage-500</title>
</head>
<body>

<%
    int x = 1/0;
%>

</body>
</html>
