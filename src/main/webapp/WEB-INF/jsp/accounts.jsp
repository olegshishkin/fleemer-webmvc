<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp" %>
<jsp:include page="navbar.jsp"><jsp:param name="title" value="Accounts"/></jsp:include>
<main role="main" class="container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-4">
                <h5 class="text-center">Add account</h5>

                <c:if test="${!empty existenceError}">
                    <div class="alert alert-danger text-center" role="alert">
                            ${existenceError}
                    </div>
                </c:if>

                <form:form action="/accounts/create" method="post" modelAttribute="account">
                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="name">Name</form:label>
                            <form:input path="name" type="text" class="form-control" required="true"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="type">Type</form:label>
                            <form:select path="type" class="custom-select d-block w-100" required="true">
                                <option value="">Choose...</option>
                                <c:forEach items="${accountTypes}" var="t">
                                    <form:option value="${t.ordinal()}">${t.name()}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="currency">Currency</form:label>
                            <form:select path="currency" class="custom-select d-block w-100" required="true">
                                <option value="">Choose...</option>
                                <c:forEach items="${currencies}" var="c">
                                    <form:option value="${c.ordinal()}">${c.name()}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <button class="btn btn-primary btn-block" type="submit">Submit</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-md-6 mb-3">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                        <th scope="col">Currency</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${accounts}" var="a">
                        <tr>
                            <th>${a.name}</th>
                            <td>${a.type}</td>
                            <td>${a.currency}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@include file="footer.jsp" %>