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
    <form method="get" action="/Ideas/" onsubmit="this.action = this.action + this.date_ideas.value; refresh()">
    <p><b><c:out value="Дата"/></b></p>
        <input type="text" id="date_ideas" size= "40">
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
<script>
    function refresh() {
        window.opener.location.reload();
    }
</script>
</body>

</html>
