<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="budget.schema.User" %>
<%
    // Session validation
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp?msg=unauthorized");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Budget Tracker</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="https://kit.fontawesome.com/5d6c43337b.js" crossorigin="anonymous"></script>
    <script src="Script.js" defer></script>
</head>
<body>

<!-- Sidebar -->
<div id="sidebar" class="sidebar">
    <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
    <h2 id="sidebar-title">Budget Tracker</h2>
    <ul>
        <li><i class="fa-solid fa-house-user"></i> <span class="menu-text"><a href="dashboard">Dashboard</a></span></li>
        <li><i class="fa-solid fa-money-bill-trend-up"></i> <span class="menu-text"><a href="expense">Expenses</a></span></li>
        <li><i class="fa-solid fa-money-check-dollar"></i> <span class="menu-text"><a href="budget">Budgets</a></span></li>
        <li><i class="fa-solid fa-bullseye"></i> <span class="menu-text"><a href="goal">Goals</a></span></li>
        <li><i class="fa-solid fa-coins"></i> <span class="menu-text"><a href="analytics.jsp">Analytics</a></span></li>
        <li><i class="fa-solid fa-right-from-bracket"></i> <span class="menu-text"><a href="logout">Logout</a></span></li>
    </ul>
</div>

<!-- Main Content -->
<div id="main-content">
