<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <title>Планировщик задач - LifePlanner</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        .block1
        {
            border-style:solid;
            padding: 10px;
        }
        .block2
        {
            border-style:solid;
            padding: 10px;
        }
        .block3
        {
            border-style:solid;
            padding: 10px;
        }
        .block4
        {
            border-style:solid;
            padding: 10px;
        }
        h1{
            font-size: 16pt;
        }
    </style>
</head>

<body>


<div class="block1">
    <h1>Загрузить LifePlan</h1>
    <form method="post" action="/Ctrl?name=upload" enctype="multipart/form-data">
        Choose a file: <input type="file" name="file" />
        <input type="submit" value="Upload"/>
    </form>
</div>
<p>
<%--$ вызывает метод toString объекта, который передается в request--%>
<c:if test="${leaves.size() > 0}">
<div class="block2">
    <h1>План на день</h1>
    <form action="/Ctrl" method="get" >
        <p><b><c:out value="Date"/></b><br>
            <input type="text" name="Date" size= "40">
        <c:forEach var="leaf" items="${leaves}" varStatus="loop">
            <p><b><c:out value="${leaf.name}"/></b><br>
                <input type="text" name="${leaf.name}" size="40">
            </p>
        </c:forEach>
        <input type="hidden" name="name" value="setLeafPlan" size="40">
        <p><input type="submit" value="OK"></p>
    </form>
</div>
<p>
<div class="block3">
    <h1>Выгрузить весь LifePlan</h1>
    <form action="/Ctrl" method="get" enctype="text/plain">
        <input type="hidden" name="name" value="get" size="40">
        <p><button type="submit" id="btn_get">OK</button></p>
    </form>
</div>
</c:if>
<p>
<div class="block4">
    <h1>Идея/мысль</h1>
    <form action="/Ideas/new" method="get" enctype="text/plain">
    <p><button type="submit" id="btn_ideas_new">Идея/мысль</button></p>
</form>
</div>
<div class="block1">
    <h1>Духовное развитие</h1>
    <form action="/SpiritDev/new" method="get" enctype="text/plain">
        <p><button type="submit" id="spirit_new">OK</button></p>
    </form>
</div>






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


