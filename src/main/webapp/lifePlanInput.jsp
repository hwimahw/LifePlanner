<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>

</head>

<body>

<form method="post" action="/servlet" enctype="multipart/form-data">
    Choose a file: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

<c:if test="${leaves.size() > 0}">
    <form action="/servlet" method="get">
        <c:forEach var="leaf" items="${leaves}" varStatus="loop">
            <p><b><c:out value="${leaf.name}"/></b><br>
                <input type="text" name="${leaf.name}" size="40">
            </p>
        </c:forEach>
        <p><input type="submit" value="Отправить"></p>
    </form>
</c:if>

</body>


