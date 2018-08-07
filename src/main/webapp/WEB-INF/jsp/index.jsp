<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp" %>
<jsp:include page="navbar.jsp"><jsp:param name="title" value="Home"/></jsp:include>
<main role="main" class="container">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted">Total balance</span>
                    <span id="totalBalance" class="badge badge-warning badge-pill"></span>
                </h4>
                <ul id="accountSummaryPlace" class="list-group mb-3"></ul>
            </div>

            <div class="col-md-8">
                <div class="text-center">
                    <h5 class="text-center">Add operation</h5>
                </div>

                <div class="d-block my-3 text-center">
                    <div class="custom-control custom-radio custom-control-inline">
                        <input id="outcome" name="operationType" type="radio" class="custom-control-input" required>
                        <label class="custom-control-label" for="outcome">Outcome</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input id="income" name="operationType" type="radio" class="custom-control-input" checked required>
                        <label class="custom-control-label" for="income">Income</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input id="transfer" name="operationType" type="radio" class="custom-control-input" required>
                        <label class="custom-control-label" for="transfer">Transfer</label>
                    </div>
                </div>

                <form:form action="/operation/new" method="post" modelAttribute="operation" cssClass="needs-validation" novalidate="true">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <form:select path="outAccountName" cssClass="custom-select d-block w-100">
                                <form:option value="" label="Select account"/>
                                <form:options items="${accounts}" itemValue="name" itemLabel="name"/>
                            </form:select>
                            <form:errors path="outAccountName" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a value</div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <form:select path="inAccountName" cssClass="custom-select d-block w-100" disabled="true">
                                <form:option value="" label="Select account"/>
                                <form:options items="${accounts}" itemValue="name" itemLabel="name"/>
                            </form:select>
                            <form:errors path="inAccountName" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a value</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <select id="categoryName" name="categoryName" class="custom-select d-block w-100" title=""></select>
                            <div class="invalid-feedback">Select a value</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <form:input path="operation.date" cssClass="form-control" placeholder="yyyy-mm-dd"
                                        type="text" required="true"/>
                            <form:errors path="operation.date" cssClass="text-danger"/>
                            <div class="invalid-feedback">Select a date of operation</div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <form:input path="operation.sum" type="text" cssClass="form-control" placeholder="0.00"
                                        pattern="[0-9]+(\.[0-9]+)?" required="true" autofocus="true"/>
                            <form:errors path="operation.sum" cssClass="text-danger"/>
                            <div class="invalid-feedback">The field cannot be empty and should be a digit</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:textarea path="operation.comment" cssClass="form-control" rows="1" placeholder="Comment"/>
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

        <div class="row">
            <div class="col-md-12 mb-3"></div>
        </div>

        <div class="row">
            <div id="operationTable" class="col-md-12 mb-3"></div>
        </div>

        <div class="row">
            <div class="col-md-12 mb-3">
                <nav>
                    <ul id="pagination" class="pagination justify-content-center"></ul>
                </nav>
            </div>
        </div>
        <%--Code snippet for account information appending--%>
        <div hidden>
            <li id="accountSummary" class="d-flex justify-content-between lh-condensed">
                <div>
                    <h6 class="my-0"></h6>
                    <small class="text-muted"></small>
                </div>
                <span class="text-muted"></span>
            </li>
        </div>
        <%--Code snippet for operation table--%>
        <div hidden>
            <table id="operationTableSnippet" class="table table-hover">
                <thead align="right">
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">From account</th>
                    <th scope="col">To account</th>
                    <th scope="col">Category</th>
                    <th scope="col">Sum</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <%--Code snippet for pagination--%>
        <div hidden>
            <li id="curPage" class="page-item"><a href="#pagination" class="page-link"></a></li>
        </div>
<%@include file="footer.jsp" %>