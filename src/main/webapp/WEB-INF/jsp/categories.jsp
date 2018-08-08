<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp" %>
<jsp:include page="navbar.jsp"><jsp:param name="title" value="Categories"/></jsp:include>
<main role="main" class="container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-4">
                <h5 class="text-center">Add category</h5>

                <form:form action="/category/new" method="post" modelAttribute="category" cssClass="needs-validation" novalidate="true">
                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="name">Name</form:label>
                            <form:input path="name" type="text" cssClass="form-control" required="true"/>
                            <form:errors path="name" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:label path="type">Type</form:label>
                            <form:select path="type" cssClass="custom-select d-block w-100" required="true">
                                <form:option value="" label="Select..."/>
                                <form:options items="${categoryTypes}"/>
                            </form:select>
                            <form:errors path="type" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a value</div>
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
                    <thead align="right">
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${categories}" var="a">
                        <tr>
                            <td align="right">${a.name}</td>
                            <td align="right">${a.type}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function(){
            setValidationListener();
        });
    </script>
<%@include file="footer.jsp" %>