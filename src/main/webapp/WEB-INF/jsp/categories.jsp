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
                <form>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <label for="type">Type</label>
                            <select class="custom-select d-block w-100" id="type" required>
                                <option value="">Choose...</option>
                                <option>Bank</option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <label for="name">Name</label>
                            <input type="text" class="form-control" id="name" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <button class="btn btn-primary btn-block" type="submit">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-md-6 mb-3">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>1</th>
                        <td>1</td>
                    </tr>
                    <tr>
                        <th>1</th>
                        <td>1</td>
                    </tr>
                    <tr>
                        <th>1</th>
                        <td>1</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <footer class="my-5 text-muted text-center text-small">
        <p class="mb-3">&copy; 2018 Fleemer Corp.</p>
    </footer>
</main>

</body>
</html>