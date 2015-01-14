<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Serie Form</title>
</head>
<body>

<c:choose>
    <c:when test="${serie.getId()>0}">
        <h3>Update Serie</h3>
        <c:set var="method" value="PUT"/>
        <c:set var="action" value="/series/${serie.getId()}"/>
    </c:when>
    <c:otherwise>
        <h3>Create Serie</h3>
        <c:set var="method" value="POST"/>
        <c:set var="action" value="/series"/>
    </c:otherwise>
</c:choose>

<form:form method="${method}" action="${action}" modelAttribute="serie">
    <table>
        <tr>
            <td><form:label path="title">Title</form:label></td>
            <td><form:input path="title"/> <i><form:errors path="title"></form:errors></i></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form:form>

</body>
</html>
