<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--<form name="test" method="post" action="input">--%>
<head>

</head>

<body>

<form method="post" action="/servlet" enctype="multipart/form-data">
    Choose a file: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

    <c:forEach var="leaf" items="${leaves}">
        <p><b><c:out value="${leaf.name}"/></b><br>
            <input type="text" size="40">
        </p>
    </c:forEach>
    <p><input type="submit" value="Отправить"></p>

</body>

<%--</form>--%>


