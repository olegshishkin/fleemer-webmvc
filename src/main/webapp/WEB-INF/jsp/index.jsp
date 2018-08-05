<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp" %>
<jsp:include page="navbar.jsp"><jsp:param name="title" value="Home"/></jsp:include>

<script type="text/javascript">
    function disableTransfer(){
        var tagForDisable;
        var tagForEnable;
        if (operationTypeSelector.id == 'outcome') {
            tagForDisable = document.getElementById("category");
        }
        tagForDisable.disabled = true;
    }
</script>
<main role="main" class="container">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted">Total amount</span>
                    <span class="badge badge-secondary badge-pill">3</span>
                </h4>
                <ul class="list-group mb-3">
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0">Product name</h6>
                            <small class="text-muted">Brief description</small>
                        </div>
                        <span class="text-muted">12</span>
                    </li>
                </ul>
            </div>

            <div class="col-md-8">
                <div class="d-block my-3 text-center">
                    <div class="custom-control custom-radio custom-control-inline">
                        <input id="outcome" name="operationType" type="radio" class="custom-control-input" checked required>
                        <label class="custom-control-label" for="outcome">Outcome</label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <input id="income" name="operationType" type="radio" class="custom-control-input" required>
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
                            <form:select path="inAccount" cssClass="custom-select d-block w-100" required="true">
                                <form:option value="0" label="Select account"/>
                                <form:options items="${accounts}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="inAccount" cssClass="invalid-feedback"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <form:select path="outAccount" cssClass="custom-select d-block w-100">
                                <form:option value="0" label="Select account"/>
                                <form:options items="${accounts}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="outAccount" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:select path="category" cssClass="custom-select d-block w-100" required="true">
                                <form:option value="0" label="Select category"/>
                                <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="category" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <form:input path="date" cssClass="form-control" placeholder="dd.mm.yyyy"
                                        type="text" required="true"/>
                            <form:errors path="date" cssClass="invalid-feedback"/>
                            <div class="invalid-feedback">Select a date of operation</div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <form:input path="sum" type="text" cssClass="form-control" placeholder="0.00"
                                        pattern="[0-9]+(\.[0-9]+)?" required="true" autofocus="true"/>
                            <form:errors path="sum" cssClass="invalid-feedback"/>
                            <div class="invalid-feedback">The field cannot be empty and should be a digit</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <form:textarea path="comment" cssClass="form-control" rows="1" placeholder="Comment"/>
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
            <div class="col-md-12 mb-3">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Date</th>
                        <th scope="col">From account</th>
                        <th scope="col">To account</th>
                        <th scope="col">Category</th>
                        <th scope="col">Sum</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${operations}" var="o">
                        <tr>
                            <th>${o.date}</th>
                            <td>${o.inAccount}</td>
                            <td>${o.outAccount}</td>
                            <td>${o.category}</td>
                            <td class="text-danger">
                                <c:choose>
                                    <c:when test="${o.inAccount != null && o.outAccount != null}">
                                        <td class="text-danger">${o.sum}</td>
                                    </c:when>
                                    <c:when test="${o.inAccount != null && o.category != null}">
                                        <td class="text-success">${o.sum}</td>
                                    </c:when>
                                    <c:otherwise><td>${o.sum}</td></c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 mb-3">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled"><a class="page-link" href="#" tabindex="-1">Previous</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item active"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </nav>
            </div>
        </div>
<%@include file="footer.jsp" %>