<html>
<head>
    <title>User Form</title>
</head>
<body>
<h2>User Form</h2>
<form action="${pageContext.request.contextPath}/update-user" method="post">
    <label for="userId">User Id:</label><br>
    <input type="text" id="userId" name="uid" required><br>
    <label for="role">User Role: ADMINISTRATOR/REGULAR</label><br>
    <input type="text" id="role" name="role" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>