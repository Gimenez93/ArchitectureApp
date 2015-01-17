<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Search form</title>
</head>
<body>


<form action="/search" method="post">
Title:<br>
<input type="text" name="title"><br>
<br><br>
<input type="submit" value="Submit">
</form>
</body>
</html>
