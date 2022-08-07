<%@ page import="java.util.List" %>
<%@ page import="model.entity.Course" %>
<%@ page import="model.service.CourseService" %>
<%@ page import="java.io.Writer" %>
<%@ page import="model.entity.User" %>
<%@ page import="controller.servlets.utils.CourseViewer" %>
<%@ page import="controller.constants.ControllerConstants" %>
<%@ page import="controller.command.Command" %>
<%@ page import="controller.command.impl.goToMainPageCommand" %><%--
  Created by IntelliJ IDEA.
  User: k1dep
  Date: 29.07.2022
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<%= request.getSession().getAttribute("message") == null? "": request.getSession().getAttribute("message")%>
<% request.getSession().setAttribute("message", null); %>
<%
    Command command = new goToMainPageCommand();
    Writer writer = response.getWriter();
    writer.write(command.execute(request, response));
%>
</body>
</html>
