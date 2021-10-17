<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<html>
<body>

<h2>Hello World!</h2>

<%-- 这里提交的路径，需要寻找到项目的路径 --%>
<%-- ${pageContext.request.contextPath}代表当前的项目 --%>
<%-- <%=request.getContextPath()%>：/项目名(/ckr) --%>

<%--<form action="${pageContext.request.contextPath}/login" method="get">--%>
<form action="<%=request.getContextPath()%>/login" method="get">
    <h3>用户名：</h3> <input type="text" name="username"> <br>
    <h3>密&nbsp;&nbsp;&nbsp;码：</h3> <input type="password" name="password"> <br>
    <input type="submit">
</form>

</body>
</html>
