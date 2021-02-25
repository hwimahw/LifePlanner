<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
        h1{
            font-size: 16pt;
        }
    </style>
</head>

<body>
<div class="block1">
    <h1>Новая идея/мысль</h1>
    <form method="post" action="/Ideas">
    <p><b><c:out value="Дата"/></b><br>
        <input type="text" id="date" name="date" size= "40">
    <p><b><c:out value="Мысль/Идея"/></b><br>
        <input type="text" name="idea" size= "40">
    <p><button type="submit" id="idea_thought_btn">OK</button></p>
</form>
</div>
<p>
<div class="block2">
    <h1>Записанные идеи по дате</h1>
    <form method="get" id="form" onsubmit="urlBuild('date_ideas')">
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
    <form method="post" id="edit_form" onsubmit="urlBuild('id')">
        <p><b><c:out value="Номер (id)"/></b></p>
        <input type="hidden" name="_method" value="PATCH">
        <input type="text" id="id" name="id" size= "40">
        <input type="text" id="new_idea" name="new_idea" size= "40">
        <p><button type="submit" id="edit_btn">OK</button></p>
    </form>
</div>
<script>
    function urlBuild(element) {
        var action_src = "/Ideas/" + document.getElementsByName(element)[0].value;
        var form = document.getElementById('form');
        form.action = action_src;
    }
</script>
</form>
</div>
</body>

</html>
