<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%--
JSTL标签库使用：在 Tomcat 中也需要引入jstl的jar包，否则会报错：JSTL解析错误。
无法在web.xml或使用此应用程序部署的jar文件中解析绝对uri：[http://java.sun.com/jsp/jstl/core]
在 Tomcat 的 lib目录下导入jstl-api-1.2.jar和standard-1.1.2.jar。

JSTL表达式：
JSTL标签库的使用就是为了弥补 HTML 标签的不足；它自定义了许多标签，可以供我们使用。标签的功能和 Java 代码一样！
--%>

<html>
<head>
    <title>core_if</title>
</head>
<body>

<h3>核心标签：if测试</h3>

<hr>

<form action="core_if.jsp" method="get">

    <%-- EL表达式：获取表单中的数据 ${param.参数名} --%>
    <input type="text" name="username" value="${param.username}">
    <input type="submit" value="登录">

</form>

<%-- 判断：如果提交的用户名是管理员，则登录成功 --%>
<c:if test="${param.username == 'admin'}" var="isAdmin">
    <c:out value="你好，管理员！盖牌认输永远不在选择之列！"/>
</c:if>

<%-- 自闭合标签，"/" --%>
isAdmin:<c:out value="${isAdmin}"/>

</body>
</html>
