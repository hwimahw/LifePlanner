<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
Регистрация
</head>
<body>

<form action="/register" method="post">

    <p><b><c:out value="Логин"/></b><br>
        <input type="text" name="login" size="40">
    <p><b><c:out value="Пароль"/></b><br>
        <input type="text" name="password" size="40">
    <p><input type="submit" value="ОК"></p>
</form>

<form action="/logInPage" method="get">
<%--        <input type="text" name="action" value="logInPage" size="40" hidden>--%>
    <p><input type="submit" value="Вход"></p>
</form>
</body>
</html>