<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-9
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello JSP</title>
  </head>
  <body>

  <%--
  JSP表达式 作用：用来将程序的输出，输出到客户端。
 <%= 变量或者表达式%>
 --%>

  <%= new Date()%>
  <br>
  <%= new SimpleDateFormat()%>
  <br>
  <%= 6+6%>

  <hr>

  <%--jsp脚本片段 <% %> --%>
  <%
  int sum = 0;
    for (int i = 1; i <=100 ; i++) {
      sum += i;
    }
//    在jsp中，System.out.println()的内容是输出在控制台，而out.println()的内容是输出在页面中。
    System.out.println(sum);// 输出到控制台（Server）
    out.println(sum);
    out.println("<br>");
    out.write("盖牌认输永远不在选择之列！");
    out.println("<h1>sum = " + sum + "</h1>");
    /*
    当我们通过out.print()向页面输出数据时，无论你输入的是什么类型，它都会给你强制类型转换成String类型。
    当我们通过out.write()向页面输出数据时，特别是输出int类型时，底层代码会将你输入的int类型的数据转换成char类型，
    所以你明白为什么当你输出int型时它打印的是一些奇奇怪怪的字符了。
    */
  %>

  <%--jsp脚本片段的再实现--%>
  <%
    int x = 10;
    out.println(x);
  %>
  <p>这是一个JSP页面</p>
  <%
    int y = 666;
    out.println(y);
  %>

  <hr>

  <%--在代码里嵌入HTML元素--%>
  <%--EL表达式--%>
  <%--Expression Language表达式，常用于取值，EL表达式可以使我们的取值代码更加简洁：${}--%>
  <%for (int i = 0; i < 5; i++) {%>
  <h1>Hello,World <%= i%></h1>
  <% } %>

  <hr>

  <%--JSP声明--%>
  <%--JSP声明中的代码会被编译到JSP生成Java的类中！其它的（jsp脚本片段和jsp表达式）则会被生成到_jspService方法中！--%>
  <%!
  static {
    System.out.println("Loading Servlet!");
  }

  private int globalVar = 0;

  public void printName(){
    System.out.println("Cayde 6");
  }
  %>

  <%
    printName();
  %>

  <!--JSP的注释不会在客户端显示，HTML会显示！-->

  <!--我是 HTML 的注释-->
  <%--我是 JSP 的注释--%>

  </body>
</html>
