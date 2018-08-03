<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="<c:url value="/"/>">
        <img src="<c:url value="/static/images/logo_full.png"/>" width="135" height="35" alt="Fleemer">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${param.title.equals('Main')}"><li class="nav-item active"></c:when>
                <c:when test="${!param.title.equals('Main')}"><li class="nav-item"></c:when>
            </c:choose>
                <a class="nav-link" href="<c:url value="/"/>">Home</a>
            </li>
            <c:choose>
                <c:when test="${param.title.equals('Accounts')}"><li class="nav-item active"></c:when>
                <c:when test="${!param.title.equals('Accounts')}"><li class="nav-item"></c:when>
            </c:choose>
                <a class="nav-link" href="<c:url value="/"/>">Accounts</a>
            </li>
            <c:choose>
                <c:when test="${param.title.equals('Categories')}"><li class="nav-item active"></c:when>
                <c:when test="${!param.title.equals('Categories')}"><li class="nav-item"></c:when>
            </c:choose>
                <a class="nav-link" href="#">Categories</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="">Logout (Fred)</a>
            </li>
        </ul>
    </div>
</nav>