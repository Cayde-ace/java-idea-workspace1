<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>scope</title>
</head>
<body>

<%
    // 从 pageContext 中取出，我们通过寻找的方式。
    // 从底层到高层（作用域）：page->request->session->application
    String name1 = (String) pageContext.findAttribute("name1");
    String name2 = (String) pageContext.findAttribute("name2");
    String name3 = (String) pageContext.findAttribute("name3");
    String name4 = (String) pageContext.findAttribute("name4");
    String name5 = (String) pageContext.findAttribute("name5");// 不存在

//    pageContext.forward("/pageDemo01.jsp");
%>

<%--使用 EL 表达式输出：${} --%>
<h1>取出的值为：</h1>
<h3>${name1}</h3>
<h3>${name2}</h3>
<h3>${name3}</h3>
<h3>${name4}</h3>
<h3><%= name5%></h3>
<h3><%= "凯德六号"%></h3>

<%--
凯德3号
凯德4号
null
凯德六号
--%>

</body>
</html>
