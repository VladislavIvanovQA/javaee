<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Registration</title>
</head>
<body>
<form action="/registration" method="POST">
    <ul>
        <label>
            Логин: <input type="text" name="login">
        </label>
    </ul>
    <ul>
        <label>
            Пароль: <input type="text" name="password">
        </label>
    </ul>
    <ul>
        <label>
            Имя и фамилия: <input type="text" name="io">
        </label>
    </ul>
    <ul>
        <label>
            Номер телефона: <input type="text" name="phone">
        </label>
    </ul>
    <ul>
        <label>
            Почта: <input type="text" name="email">
        </label>
    </ul>
    <ul>
        <label>Пол:
            <select size="1" name="sex">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
        </label>
    </ul>
    <ul>
        <input type="submit" value="Registartion">
    </ul>
</form>
</body>
</html>
