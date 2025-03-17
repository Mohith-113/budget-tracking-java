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
      <h1><span class="l">L</span>OGIN</h1>
    </div>
    <form action="login" method="post">
      <label for="email">Email</label>
      <input type="email" class="inp" name="email" required>
      <label for="psw">Password</label>
      <input type="password" class="inp" name="psw" required>
      <button type="submit" class="sub">Enter</button>
    </form>
    <div class="signup">
      <b>Don't have account?</b>
      <a href="register">Sign up</a>
    </div>
    <b class="msgtext" style="color: red;">Message</b>
  </div>
</div>
</body>
</html>