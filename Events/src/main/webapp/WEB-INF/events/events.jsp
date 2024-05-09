<%@ page import="data.models.EventModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Events</title>
</head>
<body>
<h1>EventPad!</h1><br/>

<table>
    <tr>
        <th>
            <form action="${pageContext.request.contextPath}/events" method="get">
                <button type="submit">Events</button>
            </form>
        </th>
        <th>
            <form action="${pageContext.request.contextPath}/specifics" method="get">
                <button type="submit">Specifics</button>
            </form>
        </th>
        <th>
            <form action="${pageContext.request.contextPath}/tickets" method="get">
                <button type="submit">Tickets</button>
            </form>
        </th>
        <th>
            <form action="${pageContext.request.contextPath}/users" method="get">
                <button type="submit">Users</button>
            </form>
        </th>
    </tr>
</table>

<c:if test="${sessionScope.user.role == 'ADMINISTRATOR'}">
    <table>
        <tr>
            <th>
                <form action="${pageContext.request.contextPath}/create-event" method="get">
                    <button type="submit">Create Event</button>
                </form>
            </th>
            <th>
                <form action="${pageContext.request.contextPath}/update-event" method="get">
                    <button type="submit">Update Event</button>
                </form>
            </th>
            <th>
                <form action="${pageContext.request.contextPath}/delete-event" method="get">
                    <button type="submit">Delete Event</button>
                </form>
            </th>
        </tr>
    </table>
</c:if>

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

