<%--
  Created by IntelliJ IDEA.
  User: k1dep
  Date: 23.07.2022
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<%= request.getSession().getAttribute("message") == null? "": request.getSession().getAttribute("message")%>
<% request.getSession().setAttribute("message", null); %>
    <div class="form">
        <h1>Registration in system</h1>
        <form method="post" action="register-util">
            <input type="text" required placeholder="Name" name="firstname"><br>
            <input type="text" required placeholder="Surname" name="lastname"><br>
            <input type="text" required placeholder="login" name="login"><br>
            <input type="password" required placeholder="password" name="password"><br><br>
            <input class="button" type="submit" value="Registration">
        </form>
    </div>
</body>
</html>
