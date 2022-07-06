<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <style>
        .font {
            font-size: 30px;
            font-weight: 600;
        }
    </style>
</head>
<body>
<c:if test="${not empty error}">
    <c:out value="${error}"></c:out>;
</c:if>
<c:if test="${not empty success}">
    <c:out value="${success}"></c:out>;
</c:if>
<p class="font">Log in</p>
<form action="/logIn" method="post">
    <p><b><c:out value="Логин"/></b><br>
        <input type="text" name="login" size="40">
    <p><b><c:out value="Пароль"/></b><br>
        <input type="text" name="password" size="40">
    <p><input type="submit" value="ОК"></p>
</form>
<form action="/registerPage" method="get">
<%--    <input type="text" name="action" value="registerPage" size="40" hidden>--%>
    <p><input type="submit" value="Регистрация"></p>
</form>
</body>
</html>
