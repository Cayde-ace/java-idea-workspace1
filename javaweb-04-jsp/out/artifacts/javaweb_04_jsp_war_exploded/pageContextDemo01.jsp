<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-10
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Store</title>
</head>
<body>

<%--内置对象--%>
<%
pageContext.setAttribute("name1", "凯德1号");// 保存的数据只在一个页面中有效。
request.setAttribute("name2", "凯德2号");// 保存的数据只在一次请求中有效，请求转发会携带这个数据。
session.setAttribute("name3", "凯德3号");// 保存的数据只在一次会话中有效，从打开浏览器到关闭浏览器。
application.setAttribute("name4", "凯德4号");// 保存的数据只在服务器中有效，从打开服务器到关闭服务器。
%>

<%--脚本片段中的代码会被原封不动的生成到..._jsp.java中。要求：脚本片段里的代码必须保证 Java 语法的正确性--%>
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
凯德1号
凯德2号
凯德3号
凯德4号
null
凯德六号
--%>

</body>
</html>
