<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <title>Планировщик задач - LifePlanner</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="./prototype.js"></script>
    <style type="text/css">
    </style>
</head>

<body>

<form method="post" action="/Ctrl?name=upload" enctype="multipart/form-data">
    Choose a file: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

<%--$ вызывает метод toString объекта, который передается в request--%>
<c:if test="${leaves.size() > 0}">
    <form action="/Ctrl" method="get" >
        <p><b><c:out value="Date"/></b><br>
            <input type="text" name="Date" size= "40">
        <c:forEach var="leaf" items="${leaves}" varStatus="loop">
            <p><b><c:out value="${leaf.name}"/></b><br>
                <input type="text" name="${leaf.name}" size="40">
            </p>
        </c:forEach>
        <input type="hidden" name="name" value="setLeafPlan" size="40">
        <p><input type="submit" value="Save"></p>
    </form>
    <form action="/Ctrl?name=get" method="get" enctype="text/plain">
        <input type="hidden" name="name" value="get" size="40">
        <p><button type="submit" id="btn_get">Get LifePlan</button></p>
    </form>
</c:if>

<%--<script>--%>
<%--    function btnClick() {--%>
<%--        new Ajax.Request('http://localhost:8080/get', {--%>
<%--            method: 'get',--%>
<%--            onSuccess: function (transport) {--%>
<%--                var response = transport.responseText || "no response text";--%>
<%--            },--%>
<%--            onFailure: function () {--%>
<%--                alert('Something went wrong...')--%>
<%--            }--%>
<%--        });--%>


<%--    }--%>
<%--    document.observe('dom:loaded',--%>
<%--        function () {--%>
<%--            Event.observe('btn_get', 'click', btnClick);--%>
<%--        }--%>
<%--    );--%>
<%--</script>--%>

</body>


