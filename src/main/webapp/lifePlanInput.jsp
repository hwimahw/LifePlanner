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

<form method="post" action="/uploadFile" enctype="multipart/form-data">
    Choose a file: <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

<c:if test="${leaves.size() > 0}">
    <form id="setDayPlan" action="/setDayPlan" method="post">
        <p><b><c:out value="Date"/></b><br>
            <input type="text" id="date" name="date" size="40">
            <input type="text" name="leaves" value="${leaves}" hidden/>
        <c:forEach var="leaf" items="${leaves}" varStatus="loop">
            <p><b><c:out value="${leaf.name}"/></b><br>
                <input type="text" name="${leaf.name}" size="40">
            </p>
        </c:forEach>
        <p><input type="submit" value="Save"></p>
    </form>
    <form action="/get" method="get" enctype="multipart/form-data">
        <p><button type="submit" id="btn_get">Get LifePlan</button></p>
    </form>
</c:if>

<form method="get" action="/exit">
    <input type="submit" value="Exit" />
</form>

<script>

    function urlBuild(element, form_id) {
        var action_src = "/Ideas/" + document.getElementsByName(element)[0].value;
        var form = document.getElementById(form_id);
        form.action = action_src;
    }

    function validate_date(value, flag)
    {
        var arrD = value.split("-");
        arrD[1] -= 1;
        var d = new Date(arrD[0], arrD[1], arrD[2]);
        if ((d.getFullYear() == arrD[0]) && (d.getMonth() == arrD[1]) && (d.getDate() == arrD[2])) {
            if(flag === 1){
                urlBuild('date_ideas','date_ideas_form'); // дополнительный вызов необходим,
                                                          // так как событие вызова данной функции заменется
            }
            return true;
        } else {
            alert("Введена некорректная дата!");
            return false;
        }
    }

    function validate(formId, dateInputId, flag) {
        var form = document.getElementById(formId);
        var name = document.getElementById(dateInputId);
        // Навешиваем на форму обработчик отправки
        form.onsubmit = function (evt) {
            // Проверяем введённое значение на соответствие
            if (!validate_date(name.value, flag)) {
                // Если значение не подходит, отменяем автоматическую отправку формы
                evt.preventDefault();
            }
        };
    }
    validate('setDayPlan', 'date', 0);
    // validate('date_ideas_form', 'date_ideas', 1);
</script>



</body>


