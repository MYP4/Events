<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Аутентификация</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/sign-in" method="post">
    <label>
        Логин
        <input type="text" name="login">
    </label>
    <br>
    <label>
        Пароль
        <input type="password" name="password">
    </label>
    <br>
    <button type="submit">Авторизоваться</button>
    <c:if test="${not empty requestScope.message}">
        <div style="color: red"><c:out value="${requestScope.message}"/></div>
    </c:if>
</form>
<form action="${pageContext.request.contextPath}/sign-up" method="get">
    <button type="submit">Регистрация</button>
</form>
</body>
</html>
