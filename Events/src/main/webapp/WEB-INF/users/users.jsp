<%@ page import="java.util.List" %>
<%@ page import="data.models.SpecificModel" %>
<%@ page import="data.models.UserModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Users</title>
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
                <form action="${pageContext.request.contextPath}/update-user" method="get">
                    <button type="submit">Update Role</button>
                </form>
            </th></tr>
    </table>
</c:if>

<table>
    <tr>
        <th>FirstName</th>
        <th>SecondName</th>
        <th>Role</th>
        <th>AccountNumber</th>
        <th>Balance</th>
        <th>Login</th>
        <th>Uid</th>
    </tr>
    <% List<UserModel> users = (List<UserModel>) request.getAttribute("users"); %>
    <% for (UserModel user : users) { %>
    <tr>
        <td><%= user.getFirstName() %></td>
        <td><%= user.getSecondName() %></td>
        <td><%= user.getRole() %></td>
        <td><%= user.getAccountNumber() %></td>
        <td><%= user.getBalance() %></td>
        <td><%= user.getLogin() %></td>
        <td><%= user.getUid() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>
