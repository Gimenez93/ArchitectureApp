<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>

<p><a href="/users">Users</a></p>

<c:if test="${not empty user}">
    <h2>User ${user.getUsername()}</h2>
    <p>E-mail: ${user.getEmail()}</p>

    <c:if test="${not empty user.getSeries()}">
        <h3>User Series</h3>
        <c:forEach var="serie" items="${user.getSeries()}">
            <li><a href="/series/${serie.getId()}">${serie.getId()}</a>: ${fn:escapeXml(serie.getTitle())}</li>
        </c:forEach>
    </c:if>
</c:if>
		<form:form method="DELETE" action="/users/${user.getId()}">
       		<p><input type="submit" value="Delete"/></p>
    	</form:form>

</body>
</html>
