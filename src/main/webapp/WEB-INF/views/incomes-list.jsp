<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список приходов">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:focus {
            background: #FFFFD3;
            padding: 5px;
        }
    </style>
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <h2 align="center" class="page-header">Приходы</h2>
        <div class="article">
            <c:choose>
                <c:when test="${not empty incomes}">
                    <form:form method="get" action="/account/income/page" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline">
                        <div class="input-daterange input-group" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="input-sm form-control" name="start"
                                        id="start"
                                        readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue">Показать</button>
                        <input type="hidden" name="itemId" value="${rangeDto.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <script>
                            $('#datepicker-range').datepicker({
                                format: "yyyy-mm-dd",
                                todayBtn: "linked",
                                clearBtn: true
                            });
                        </script>
                    </form:form>
                    <br>
                    <table id="incomes" class="table notes">
                        <thead>
                        <tr>
                            <th>Дата</th>
                            <th>Сумма</th>
                            <th>Счет</th>
                            <th>Заметка</th>
                            <th>Редактировать заметку</th>
                            <th>Удалить приход</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="income" items="${incomes}">
                            <tr class="tableRow">
                                <td>
                                        ${income.date} ${income.time}
                                </td>
                                <td>
                                        ${income.amount}
                                </td>
                                <td>
                                        ${income.account.name}
                                </td>
                                <td class="note" data-name="tableNote" contenteditable="false">
                                        ${income.note}
                                </td>
                                <td>
                                    <div class="row editBar">
                                        <div class="col-xs-1">
                                            <form class="saveNote" action="<c:url value="/account/income/update"/>"
                                                  method="POST">
                                                <input type="text" hidden class="oldVal">
                                                <input type="hidden" name="incomeId" value="${income.id}">
                                                <input type="hidden" name="note">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                                <button type="submit" class="saveBtn" hidden>
                                                    <span class="glyphicon glyphicon-ok"></span>
                                                </button>
                                            </form>
                                            <button class="cancelBtn" hidden>
                                                <span class="glyphicon glyphicon-remove-circle"></span>
                                            </button>
                                        </div>
                                        <div class="col-xs-1">
                                            <button class="editBtn">
                                                <span class="glyphicon glyphicon-edit"></span>
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <form action="<c:url value="/account/income/delete"/>" method="POST">
                                        <input type="hidden" name="incomeId" value="${income.id}">
                                        <button type="submit">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div align="center">
                        <jsp:include page="../fragments/pagination.jsp"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <form:form method="get" action="/account/income/page" modelAttribute="rangeDto" id="rangeForm"
                                   cssClass="form-inline">
                            <div class="input-daterange input-group" id="datepicker-range">
                                <form:input path="start" type="text" cssClass="input-sm form-control" name="start"
                                            id="start"
                                            readonly="true"/>
                                <span class="input-group-addon">to</span>
                                <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end"
                                            readonly="true"/>
                            </div>
                            <button type="submit" class="btn btn-blue">Показать</button>
                            <input type="hidden" name="itemId" value="${rangeDto.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <script>
                                $('#datepicker-range').datepicker({
                                    format: "yyyy-mm-dd",
                                    todayBtn: "linked",
                                    clearBtn: true
                                });
                            </script>
                        </form:form>
                        <br>
                        <h3>Пока нет приходов по данному счету.</h3>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:master-page>

