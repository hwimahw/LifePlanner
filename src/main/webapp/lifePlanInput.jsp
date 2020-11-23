<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--<form name="test" method="post" action="input">--%>
<head>

</head>

<body>
<%--a = <c:out value="${}"/>--%>


    <c:forEach var="leaf" items="${leaves}">
        <p><b><c:out value="${leaf.name}"/></b><br>
            <input type="text" size="40">
        </p>
    </c:forEach>
    <p><input type="submit" value="Отправить"></p>

</body>

<%--</form>--%>


