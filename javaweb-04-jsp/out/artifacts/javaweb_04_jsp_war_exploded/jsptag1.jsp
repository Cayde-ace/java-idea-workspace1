<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsptag</title>
</head>
<body>

<%-- jsp标签 --%>
<%--jsp:include--%>

<%--
http://localhost:8080/jsptag1.jsp?name=ckr&age=23&sex=man
--%>

<jsp:forward page="/jsptag2.jsp">
    <jsp:param name="name" value="ckr"/>
    <jsp:param name="age" value="23"/>
    <jsp:param name="sex" value="man"/>
</jsp:forward>

</body>
</html>
