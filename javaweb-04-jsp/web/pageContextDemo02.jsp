<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>forward</title>
</head>
<body>

<%
    pageContext.forward("/pageContextDemo01.jsp");
//    request.getRequestDispatcher("/pageContextDemo01.jsp").forward(request, response);
%>

</body>
</html>
