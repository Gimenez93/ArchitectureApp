<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>

<p><a href="/series">Series</a></p>

<c:if test="${not empty serie}">
    <h2>Serie number ${serie.getId()}</h2>
    <p>Message: ${fn:escapeXml(serie.getTitle())} (<a href="/series/${serie.getId()}/form">edit</a>)</p>
    <p>Title: ${serie.getTitle()}</p>

    <form:form method="DELETE" action="/series/${serie.getId()}">
        <p><input type="submit" value="Delete"/></p>
    </form:form>
</c:if>

</body>
</html>
