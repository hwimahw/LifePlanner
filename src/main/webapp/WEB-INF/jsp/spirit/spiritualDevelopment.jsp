<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>Духовное развитие</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        .block1
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
        <h1>Новый пункт</h1>
        <form:form id="new_idea_form" method="post" action="/SpiritDev">
            <p><b><c:out value="Пункт"/></b><br>
                <input type="text" id="item" name="item" size= "40">
            <p><b><c:out value="Категория"/></b><br>
                <input type="text" id="category" name="category" size= "40">
<%--            <p><b><c:out value="id"/></b><br>--%>
<%--                <input type="text" id="id" name="id" size= "40">--%>
            <p><button type="submit" id="item_btn">OK</button></p>
        </form:form>
    </div>
</body>