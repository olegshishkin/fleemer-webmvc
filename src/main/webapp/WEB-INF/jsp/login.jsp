<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" href="<c:url value="/static/images/logo.ico"/>">
    <title>Fleemer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="<c:url value="/static/css/signin.css"/>" rel="stylesheet">
    <script src="<c:url value="/static/js/custom.js"/>" defer></script>
</head>
<body>
<form action="<c:url value="/login"/>" method="post" class="form-signin text-center">
    <sec:csrfInput />
    <img class="mb-4" src="<c:url value="/static/images/logo_full.png"/>" alt="" width="259" height="75">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger text-center" role="alert">
            Invalid username or password.
        </div>
    </c:if>
    <label for="username" class="sr-only">Username</label>
    <input type="email" id="username" name="username" class="form-control" placeholder="Email address" required autofocus/>
    <div class="valid-feedback">Correct</div>
    <div class="invalid-feedback">Please input your email address</div>

    <label for="password" class="sr-only">Password</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required/>
    <div class="valid-feedback">Correct</div>
    <div class="invalid-feedback">Please input password</div>

    <button type="submit" class="btn btn-outline-primary">Sign in</button>
    <br><br>
    <div>
        <a href="<c:url value="/user"/>" class="btn btn-link text-dark btn-sm" role="button">Register</a>
    </div>
    <footer class="text-muted text-center text-small">
        <p >&copy; 2018 Fleemer Corp.</p>
    </footer>
</body>
</html>