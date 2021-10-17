<%@ page import="com.ckr.pojo.user" %>
<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-12
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>javabean</title>
</head>
<body>

<%
//    user user = new user();
//    user.setId(1);
//    user.setName("凯德六号");
//    user.setAge(23);
//    user.setAddress("土卫六");
%>

<jsp:useBean id="user" class="com.ckr.pojo.user" scope="page"/>

<jsp:setProperty name="user" property="id" value="1"/>
<jsp:setProperty name="user" property="name" value="凯德六号"/>
<jsp:setProperty name="user" property="age" value="23"/>
<jsp:setProperty name="user" property="address" value="土卫六"/>

ID：<%= user.getId()%> <br>
姓名：<%= user.getName()%> <br>
年龄：<%= user.getAge()%> <br>
地址：<%= user.getAddress()%> <br>

<hr>

ID：<jsp:getProperty name="user" property="id"/> <br>
姓名：<jsp:getProperty name="user" property="name"/> <br>
年龄：<jsp:getProperty name="user" property="age"/> <br>
地址：<jsp:getProperty name="user" property="address"/> <br>

</body>
</html>
