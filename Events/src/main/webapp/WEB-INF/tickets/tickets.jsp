<%@ page import="data.models.EventModel" %>
<%@ page import="java.util.List" %>
<%@ page import="data.models.TicketModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <form action="${pageContext.request.contextPath}/create-ticket" method="get">
    <button type="submit">Create Ticket</button>
    </form>
    </th></tr>
    </table>
</c:if>

<table>
    <tr>
        <th>UserId</th>
        <th>SpecificId</th>
        <th>Status</th>
        <th>Uid</th>
    </tr>
    <% List<TicketModel> tickets = (List<TicketModel>) request.getAttribute("tickets"); %>
    <% for (TicketModel ticket : tickets) { %>
    <tr>
        <td><%= ticket.getUserId() %></td>
        <td><%= ticket.getSpecificId() %></td>
        <td><%= ticket.getStatus() %></td>
        <td><%= ticket.getUid() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>

