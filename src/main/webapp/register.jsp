<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Budget Tracking</title>
<link href="login.css"  type="text/css" rel="stylesheet"   />
</head>
<body>
<div class="bgc">
  <div class="container">
    <div class="header">
      <h1><span class="l">R</span>egister</h1>
    </div>
    <form action="register" method="post">
      <label for="uname">Username</label>
      <input type="text" class="inp" name="uname" required>
      <label for="email">Email</label>
      <input type="email" class="inp" name="email" required>
      <label for="psw">Password</label>
      <input type="password" class="inp" name="psw" required>
      <button type="submit" class="sub">Enter</button>
    </form>
    <div class="signup">
      <b>Already have account?</b>
      <a href="./index.jsp">Sign in</a>
    </div>
  </div>
</div>
</body>
</html>