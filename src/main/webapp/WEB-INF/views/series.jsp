<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<html>
<body>
<h2>Series List</h2>
    <ul>
    <c:if test="${not empty series}">
        <c:forEach var="serie" items="${series}">
        <li><a href="/series/${serie.getId()}">${serie.getId()}</a>: ${fn:escapeXml(serie.getTitle())}</li>
        </c:forEach>
    </c:if>
    </ul>
    <p><a href="series/serieForm">Add</a></p>
    <p><a href="series/initialize">Initialize repository</a></p>
</body>
</html>