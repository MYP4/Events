<html>
<head>
    <title>Ticket Form</title>
</head>
<body>
<h2>Ticket Form</h2>
<form action="${pageContext.request.contextPath}/create-ticket" method="post">
    <label for="specificId">Specific Id:</label><br>
    <input type="text" id="specificId" name="specificId" required><br>
    <label for="userId">User Id:</label><br>
    <input type="text" id="userId" name="userId" required><br>
    <input type="submit" value="Create">
</form>
</body>
</html>