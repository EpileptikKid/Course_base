<%--
  Created by IntelliJ IDEA.
  User: k1dep
  Date: 23.07.2022
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%= request.getSession().getAttribute("message") == null? "": request.getSession().getAttribute("message")%>
<% request.getSession().setAttribute("message", null); %>
    <div class="form">
        <h1>Login in system</h1>
        <form method="post" action="main_page">
            <label>
                <input type="text" required placeholder="login" name="login">
            </label><br>
            <label>
                <input type="password" required placeholder="password" name="password">
            </label><br><br>
            <input class="button" type="submit" value="Sign in">
        </form>
    </div>
</body>
</html>
