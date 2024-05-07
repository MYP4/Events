<%@ page import="data.models.EventModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Events</title>
</head>
<body>
<h1>EventPad!</h1><br/>
<table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Admin ID</th>
        <th>UID</th>
    </tr>
    <% List<EventModel> events = (List<EventModel>) request.getAttribute("events"); %>
    <% for (EventModel event : events) { %>
    <tr>
        <td><%= event.getName() %></td>
        <td><%= event.getDescription() %></td>
        <td><%= event.getAdminId() %></td>
        <td><%= event.getUid() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>

