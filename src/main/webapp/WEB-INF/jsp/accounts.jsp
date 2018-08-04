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

                <form:form action="/account/new" method="post" modelAttribute="account" cssClass="needs-validation" novalidate="true">
                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="name">Name</form:label>
                            <form:input path="name" type="text" cssClass="form-control" required="true"/>
                            <form:errors path="name" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="type">Type</form:label>
                            <form:select path="type" cssClass="custom-select d-block w-100" required="true">
                                <form:option value="0" label="Select..."/>
                                <form:options items="${accountTypes}"/>
                            </form:select>
                            <form:errors path="type" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a value</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="currency">Currency</form:label>
                            <form:select path="currency" cssClass="custom-select d-block w-100" required="true">
                                <form:option value="0" label="Select..."/>
                                <form:options items="${currencies}"/>
                            </form:select>
                            <form:errors path="currency" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a value</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="sum">Initial sum</form:label>
                            <form:input path="sum" type="text" cssClass="form-control" value="0"
                                        placeholder="0.0" pattern="[0-9]+(\.[0-9]+)?" required="true"/>
                            <form:errors path="sum" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty and should be a digit</div>
                            <div class="valid-feedback">Correct</div>
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