<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>Идеи/Мысли</title>
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
        .color {
            color:#ff0000;
        }
        h1{
            font-size: 16pt;
        }
    </style>
</head>

<body>
<div class="block1">
    <h1>Новая идея/мысль</h1>
    <form:form id="new_idea_form" method="post" action="/Ideas" modelAttribute="idea">
    <p><b><c:out value="Дата"/></b><br>
        <input type="text" id="date" name="date" size= "40">
<%--        <form:input path="date" id="date"/>--%>
    <p><b><c:out value="Мысль/Идея"/></b><br></p>
        <input type="text" name="idea" size= "40">
        <td><form:errors path="idea" cssClass="color"/></td>
        <p><button type="submit" id="idea_thought_btn">OK</button></p>
</form:form>
</div>
<p>
<div class="block2">
    <h1>Записанные идеи по дате</h1>
    <form method="get" id="date_ideas_form" onsubmit="urlBuild('date_ideas','date_ideas_form')">
    <p><b><c:out value="Дата"/></b></p>
        <input type="text" id="date_ideas" name="date_ideas" size= "40">
    <p><button type="submit" id="idea_btn">OK</button></p>
</form>
</div>
<p>
<div class="block3">
    <h1>Все записанные идеи</h1>
    <form method="get" action="/Ideas">
        <p><button type="submit" id="ideas_btn">OK</button></p>
    </form>
</div>
<p>
<div class="block4">
    <h1>Редактировать мысль</h1>
    <form method="post" id="edit_form" onsubmit="urlBuild('id', 'edit_form')">
        <p><b><c:out value="Номер (id)"/></b></p>
        <input type="hidden" name="_method" value="patch">
        <input type="text" id="id" name="id" size= "40">
        <p><b><c:out value="Мысль/Идея"/></b><br></p>
        <input type="text" id="new_idea" name="new_idea" size= "40">
        <p><button type="submit" id="edit_btn">OK</button></p>
    </form>
</div>
<p>
<div class="block4">
    <h1>Удалить мысль</h1>
    <form method="post" id="delete_form" onsubmit="urlBuild('ide', 'delete_form')">
        <p><b><c:out value="Номер (id)"/></b></p>
        <input type="hidden" name="_method" value="delete">
        <input type="text" id="ide" name="ide" size= "40">
        <p><button type="submit" id="delete_btn">OK</button></p>
    </form>
</div>
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

    validate('new_idea_form', 'date', 0);
    validate('date_ideas_form', 'date_ideas', 1);




</script>
</form>
</div>
</body>

</html>
