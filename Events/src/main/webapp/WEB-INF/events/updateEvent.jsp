<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event Form</title>
</head>
<body>
<h2>Event Form</h2>
<form action="submitEvent.jsp" method="post">
    <label for="name">Event Name:</label><br>
    <input type="text" id="name" name="name" required><br>
    <label for="description">Description:</label><br>
    <textarea id="description" name="description" rows="4" cols="50" required></textarea><br>
    <label for="adminId">Admin ID:</label><br>
    <input type="text" id="adminId" name="adminId" required><br>
    <label for="uid">Event ID:</label><br>
    <input type="text" id="uid" name="uid" required><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
