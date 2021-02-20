<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <title>Планировщик задач - LifePlanner</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="STYLESHEET" type="text/css" href="../../codebase/dhtmlxgrid.css">
    <link rel="STYLESHEET" type="text/css" href="../../codebase/dhtmlxgrid.css">
    <script src="../../codebase/dhtmlxcommon.js"></script>
    <script src="../../codebase/dhtmlxgrid.js"></script>
    <script src="../../codebase/dhtmlxgridcell.js"></script>
    <script src="../../codebase/prototype.js"></script>
    <style type="text/css">
    </style>
</head>

<body>



<form method="post" action="/Ctrl/process?name=upload" enctype="multipart/form-data">
    Choose a file: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

<%--$ вызывает метод toString объекта, который передается в request--%>
<c:if test="${leaves.size() > 0}">

    <form action="/Ctrl/process" method="get" >
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
    <form action="/Ctrl/process" method="get" enctype="text/plain">
        <input type="hidden" name="name" value="get" size="40">
        <p><button type="submit" id="btn_get">Get LifePlan</button></p>
    </form>


</c:if>

<form method="get" action="/Ideas">
    <p><button type="submit" id="ideas_btn">All ideas</button></p>
</form>

<form method="get" action="/Ideas/" onsubmit="this.action = this.action + this.date_ideas.value">
    <input type="text" name="date" id="date_ideas" size= "40">
    <p><button type="submit" id="idea_btn">IdeasByDate</button></p>
</form>

<form method="post" action="/Ideas">
    <p><b><c:out value="Дата"/></b><br>
        <input type="text" id="date" name="date" size= "40">
    <p><b><c:out value="Мысль/Идея"/></b><br>
        <input type="text" name="idea" size= "40">
    <p><button type="submit" id="idea_thought_btn">Get</button></p>
</form>




<%--<input type="submit" id="ass" value="AAAA" />--%>



<%--<script>--%>
<%--    function btnClick1() {--%>
<%--        new Ajax.Request('http://localhost:8080/Ctrl/process', {--%>
<%--            method: 'get',--%>
<%--            onSuccess: function (transport) {--%>
<%--                var response = transport.responseText || "no response text";--%>
<%--                var y = response.evalJSON(true);--%>
<%--            },--%>
<%--            onFailure: function () {--%>
<%--                alert('Something went wrong...')--%>
<%--            }--%>
<%--        });--%>


<%--    }--%>

<%--    document.observe('dom:loaded',--%>
<%--        function () {--%>
<%--            Event.observe('ass', 'click', btnClick1());--%>
<%--        }--%>
<%--    );--%>
<%-- </script>--%>

</body>


