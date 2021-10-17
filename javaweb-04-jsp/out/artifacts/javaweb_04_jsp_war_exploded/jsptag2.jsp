<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsptag</title>
</head>
<body>

<%--转发过来取出参数--%>

Name:<%= request.getParameter("name")%> <br>
Age:<%= request.getParameter("age")%> <br>
Sex:<%= request.getParameter("sex")%> <br>

</body>
</html>
