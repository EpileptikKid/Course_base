<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>JSP - Hello World</title></head>
<body>
    <h1><%= "Main page" %></h1>
    <h3>
        <%= request.getSession().getAttribute("message") == null? "": request.getSession().getAttribute("message")%>
        <% request.getSession().setAttribute("message", null); %>
        <a href="registration">Registration</a><br><br>
        <a href="signin">Sign in</a>
    </h3>
</body>
</html>