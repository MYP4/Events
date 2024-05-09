<html>
<head>
    <title>Specific Form</title>
</head>
<body>
<h2>Specific Form</h2>
<form action="${pageContext.request.contextPath}/delete-specific" method="post">
    <label for="specificId">Specific Id:</label><br>
    <input type="text" id="specificId" name="uid" required><br>
    <input type="submit" value="Delete">
</form>
</body>
</html>