<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-12
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OnlineCount</title>
</head>
<body>

<h1>
    当前有<span style="color: darkviolet"><%= application.getAttribute("OnlineCount")%></span>人在线！
</h1>

</body>
</html>
