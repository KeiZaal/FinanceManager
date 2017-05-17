<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Регистрация">
    <div class="container">
        <div class="col-sm-3">
            <c:url var="registrationURL" value="/registration"/>
            <form:form method="POST" action="${registrationURL}" modelAttribute="registrationDto">
                <h1>Регистрация</h1>
                <form:input cssClass="form-control" path="email" placeholder="Email"/><br>
                <form:errors path="email" cssStyle="color: red"/><br>
                <form:input path="firstName" placeholder="Имя"/><br>
                <form:errors path="firstName" cssStyle="color: red"/><br>
                <form:input path="lastName" placeholder="Фамилия"/><br>
                <form:errors path="lastName" cssStyle="color: red"/><br>
                <select class="form-control" name="currency">
                    <option disabled>--- Выберите валюту ---</option>
                    <c:forEach items="${currencies}" var="currency">
                        <option value="${currency.characterCode}" id="currency">${currency.name}</option>
                    </c:forEach>
                </select><br/>
                <form:input path="password" placeholder="Пароль" type="password"/><br>
                <form:errors path="password" cssStyle="color: red"/><br>
                <form:input path="confirm" placeholder="Подтвердите пароль" type="password"/><br>
                <form:errors path="confirm" cssStyle="color: red"/><br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <form:button type="submit">Зарегистрироваться</form:button>
            </form:form>
        </div>
    </div>
</t:master-page>
