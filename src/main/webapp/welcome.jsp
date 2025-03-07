<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="budget.schema.User" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    // Check if user session exists
    User user = (User) session.getAttribute("user");
    
    if (user == null) {
        // If no user session, redirect to login page
        response.sendRedirect("index.jsp?msg=unauthorized");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
    <h2>Welcome, <%= user.getName() %>!</h2>
    <p>Your User ID: <%= user.getUserId() %></p>
    
    <form action="logout" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>
