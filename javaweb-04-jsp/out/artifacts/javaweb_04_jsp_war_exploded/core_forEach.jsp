<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<html>
<head>
    <title>core_forEach</title>
</head>
<body>

<%
    ArrayList<String> undergraduates = new ArrayList<>();
    undergraduates.add(0, "路明非");
    undergraduates.add(1, "楚子航");
    undergraduates.add(2, "陈墨瞳");
    undergraduates.add(3, "希尔伯特·让·昂热");
    undergraduates.add(4, "凯撒·加图索");

    request.setAttribute("list", undergraduates);
%>

<%--
var:每一次遍历出来的变量
items:要遍历的对象
begin:哪里开始
end:哪里结束
step:步长
--%>

<c:forEach var="undergraduates" items="${list}">
    <c:out value="${undergraduates}"/> <br>
</c:forEach>

<hr>

<c:forEach var="undergraduates" items="${list}" begin="1" end="3" step="2">
    <c:out value="${undergraduates}"/> <br>
</c:forEach>

</body>
</html>
