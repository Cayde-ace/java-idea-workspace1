<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>include</title>
</head>
<body>

<%--@include会将多个页面合多为一--%>
<%@include file="common/header.jsp" %>
<h1>网页主体</h1>
<%--<% int i = 666; %>--%>
<%@include file="common/footer.jsp" %>

<hr>

<%--JSP标签 jsp:include：拼接页面，本质还是三个--%>
<jsp:include page="common/header.jsp"/>
<h1>网页主体</h1>
<%--<% int i = 666; %>--%>
<jsp:include page="common/footer.jsp"/>

</body>
</html>
