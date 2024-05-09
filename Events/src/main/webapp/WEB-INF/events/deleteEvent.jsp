<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event Form</title>
</head>
<body>
<h2>Event Form</h2>
<form action="${pageContext.request.contextPath}/delete-event" method="post">
    <label for="uid">Event ID:</label><br>
    <input type="text" id="uid" name="uid" required><br>
    <input type="submit" value="Delete">
</form>
</body>
</html>
