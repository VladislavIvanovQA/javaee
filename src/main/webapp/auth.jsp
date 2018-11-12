<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login</title>
</head>
<body>
<form action="/auth" method="POST">
    <input type="text" name="login" placeholder="Login">
    <input type="password" name="password" placeholder="Password">
    <input type="submit" value="Login">
</form>
<a href="pages/registration.jsp">Registration</a>
<a href="/users">List Users..</a>
<a href="/pages/createData.html">Create Data in DataBase</a>
</body>
</html>
