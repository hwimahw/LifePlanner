<%--
  Created by IntelliJ IDEA.
  User: mansur
  Date: 12.02.21
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>How to Start with dhtmlxGrid</title>
    <script type="text/javascript" src="../../codebase/grid.js"></script>
    <link rel="stylesheet" href="../../codebase/grid.css">
</head>
<body>
<div id="grid_container"></div>
<script>

    // creating dhtmlxGrid
    var grid = new dhx.Grid("grid_container", {
        columns: [
            { width: 100, id: "a", header: [{ text: "#" }] },
            { width: 100, id: "b", header: [{ text: "Title" }] },
            { width: 200, id: "c", header: [{ text: "Name" }] },
            { width: 200, id: "d", header: [{ text: "Address" }] }
        ],
        headerRowHeight: 50
    });

</script>
</body>
</html>
