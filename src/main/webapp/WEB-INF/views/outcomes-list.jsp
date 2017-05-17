<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список расходов">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:focus {
            background: #FFFFD3;
            padding: 5px;
        }
    </style>
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <h2 align="center" class="page-header">Расходы</h2>
        <div class="article">
            <c:choose>
                <c:when test="${not empty outcomes}">
                    <form:form method="get" action="/outcome/page" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline">
                        <div class="input-daterange input-group" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="input-sm form-control" name="start"
                                        id="start"
                                        readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue">Принять</button>
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
                    <table id="outcomes" class="table notes">
                        <thead>
                        <tr>
                            <th>Дата</th>
                            <th>Сумма</th>
                            <th>Счет</th>
                            <th>Заметка</th>
                            <th>Категория</th>
                            <th>Редактировать заметку</th>
                            <th>Удалить расход</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="outcome" items="${outcomes}">
                            <tr class="tableRow">
                                <td>
                                        ${outcome.date} ${outcome.time}
                                </td>
                                <td>
                                        ${outcome.amount}
                                </td>
                                <td>
                                        ${outcome.account.name}
                                </td>
                                <td class="note" data-name="tableNote" contenteditable="false">
                                        ${outcome.note}
                                </td>
                                <td>
                                        ${outcome.outcomeType.name}
                                </td>
                                <td>
                                    <div class="row editBar">
                                        <div class="col-xs-1">
                                            <form class="saveNote" action="<c:url value="/outcome/update"/>"
                                                  method="POST">
                                                <input type="text" hidden class="oldVal">
                                                <input type="hidden" name="accountId" value="${outcome.account.id}">
                                                <input type="hidden" name="outcomeId" value="${outcome.id}">
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
                                    <form action="<c:url value="/outcome/delete"/>" method="POST">
                                        <input type="hidden" name="outcomeId" value="${outcome.id}">
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
                        <h3>Пока нет расходов по данному счету.</h3>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:master-page>
