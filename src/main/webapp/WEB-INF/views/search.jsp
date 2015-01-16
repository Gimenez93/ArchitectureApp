<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Search form</title>
</head>
<body>


<form:form method="GET" action="/search?=${action}">
    <table>
        <tr>
            <td><form:label path="title">Titulo: </form:label></td>
            <td><form:input path="title"/> <i><form:errors path="title"></form:errors></i></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form:form>

</body>
</html>
