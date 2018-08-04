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
                <form:form method="post" action="/user/create" modelAttribute="person">
                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" type="email" class="form-control"
                                   pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" required="true"/>
                            <form:errors path="email" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="firstName">Firstname</form:label>
                            <form:input path="firstName" type="text" class="form-control" required="true"/>
                            <form:errors path="firstName" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="lastName">Lastname</form:label>
                            <form:input path="lastName" type="text" class="form-control"/>
                            <form:errors path="lastName" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="hash">Password</form:label>
                            <form:input path="hash" type="password" class="form-control" required="true"/>
                            <form:errors path="hash" cssClass="invalid-feedback"/>
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
    </div>
<%@include file="footer.jsp"%>