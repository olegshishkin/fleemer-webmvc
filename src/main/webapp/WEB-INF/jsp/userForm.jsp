<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp"%>
<main role="main" class="container">
    <div class="container">
        <div class="row justify-content-center mb-3">
            <img src="<c:url value="/static/images/logo.ico"/>">
        </div>
        <div class="row justify-content-center">
            <div class="col col-lg-4">
                <h5 class="text-center">Create user</h5>
                <form:form method="post" action="/user/new" modelAttribute="person" cssClass="needs-validation" novalidate="true">
                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" type="email" cssClass="form-control"
                                   pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" required="true" autofocus="true"/>
                            <form:errors path="email" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="firstName">Firstname</form:label>
                            <form:input path="firstName" type="text" cssClass="form-control" required="true"/>
                            <form:errors path="firstName" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="lastName">Lastname</form:label>
                            <form:input path="lastName" type="text" cssClass="form-control"/>
                            <form:errors path="lastName" cssClass="text-danger"/>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="hash">Password</form:label>
                            <form:password path="hash" cssClass="form-control" required="true"/>
                            <form:errors path="hash" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty</div>
                            <div class="valid-feedback">Correct</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <button class="btn btn-primary btn-block" type="submit">Submit</button>
                        </div>
                    </div>
                </form:form>

                <div class="text-center">
                    <a href="<c:url value="/login"/>" class="btn btn-link text-dark btn-sm" role="button">Back</a>
                </div>
            </div>
        </div>
    </div>
<%@include file="footer.jsp"%>