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
</head>
<body>
<div class="alert alert-danger form-signin text-center" role="alert">
    <h4 class="alert-heading">Error!</h4>
    <p>Unfortunately, an unexpected error occurred while the application was running.
        Please go to the main page of the <a href="<c:url value="/"/>">link</a>.</p>
    <hr>
    <p class="mb-0">I hope these little troubles will not affect your love for our application :)</p>
</div>
</body>
</html>