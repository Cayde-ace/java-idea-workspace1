<%--
  Created by IntelliJ IDEA.
  User: 97809
  Date: 2021-9-21
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>

<%--
GET：上传文件大小有限制
POST：上传文件大小没有限制
${pageContext.request.contextPath}：获取上下文路径，如：/file
表单如果包含一个文件上传输入项，这个表单的enctype属性就必须设置为multipart/form-data，在服务器端想获取数据就要通过流。
--%>

<form action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data" method="post">

    上传者：<label for="username"><input type="text" name="username" id="username"></label>
    <p><input type="file" name="file1"></p>
    <p><input type="file" name="file1"></p>
    <p><input type="submit" value="提交"> | <input type="reset" value="重置"></p>

</form>

</body>
</html>
