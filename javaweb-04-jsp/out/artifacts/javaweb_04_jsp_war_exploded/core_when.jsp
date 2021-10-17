<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>core_when</title>
</head>
<body>

<%-- 定义一个变量score，值为85 --%>
<c:set var="score" value="95"/>

<c:out value="${score}"/>

<c:choose>
    <%-- switch --%>
    <c:when test="${score>=90}">
        优秀啊，伙计！
    </c:when>
    <c:when test="${score>=80}">
        还可以，兄弟！
    </c:when>
    <c:when test="${score>=70}">
        加油，老伙计！
    </c:when>
    <c:when test="${score>=60}">
        努力，兄弟！
    </c:when>
</c:choose>

</body>
</html>
