<%@ page import="java.util.List" %>
<%@ page import="data.models.SpecificModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Specifics</title>
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

<table>
    <tr>
        <th>EventUid</th>
        <th>Description</th>
        <th>TicketCount</th>
        <th>Price</th>
        <th>Address</th>
        <th>Uid</th>
    </tr>
    <% List<SpecificModel> events = (List<SpecificModel>) request.getAttribute("specifics"); %>
    <% for (SpecificModel event : events) { %>
    <tr>
        <td><%= event.getEventId() %></td>
        <td><%= event.getDescription() %></td>
        <td><%= event.getTicketCount() %></td>
        <td><%= event.getPrice() %></td>
        <td><%= event.getAddress() %></td>
        <td><%= event.getUid() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>
