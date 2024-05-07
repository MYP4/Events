<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Регистрация нового пользователя</title>
</head>
<body>
<h1>Регистрация</h1><br/>
<div class="form-container">
    <div>Введите данные:</div>
    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        <label for="loginId">Логин</label>
        <input type="text" name="login" id="loginId" required><br>
        <label for="passwordId">Пароль</label>
        <input type="password" name="password" id="passwordId" required><br>
        <label for="firstNameId">Имя</label>
        <input type="text" name="firstName" id="firstNameId" required><br>
        <label for="secondNameId">Фамилия</label>
        <input type="text" name="secondName" id="secondNameId" required><br>
        <c:if test="${not empty requestScope.message}">
            <div style="color: red"><c:out value="${requestScope.message}"/></div>
        </c:if>
        <button type="submit">Регистрация</button>
    </form>
</div>
</body>
</html>
