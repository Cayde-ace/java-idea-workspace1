<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>

<%-- 方法一： --%>
<%--<%--%>
<%--    String contextPath = request.getContextPath();--%>
<%--    out.println(contextPath);// /jsp--%>
<%--    out.println("<img src='" + contextPath + "/image/500.png' alt='500'/>");--%>
<%--%>--%>

<%-- 方法二： --%>
<%-- 无"/"，代表是相对于web应用根目录,即http://localhost:8080/tomcat配置的虚拟目录/ --%>
<%--<img src="image/500.png" alt="500">--%>

<%-- 方法三： --%>
<%-- 有"/"，代表是相对于web站点根目录,即http://localhost:8080/ --%>
<img src="/jsp/image/500.png" alt="500">

<%-- 自身相对路径访问资源 --%>
<%--<img src="../image/500.png" alt="500">--%>

<%-- 注意区别相对路径访问资源，若以自身路径去访问： --%>
<%--<img src="../image/500.png" alt="500">--%>
<%-- 若以jsp1.jsp路径去访问 --%>
<%--<img src="image/500.png" alt="500">--%>

</body>
</html>
