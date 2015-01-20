<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>

<p><a href="/series">Series</a></p>

<c:if test="${not empty serie}">
    <h2>Serie number ${serie.getId()}</h2>
    <p>Title: ${fn:escapeXml(serie.getTitle())}</p>
    <p>Country: ${fn:escapeXml(serie.getCountry())}</p>
    <p>Seasons: ${fn:escapeXml(serie.getSeasons())}</p>
    <p>Airday: ${fn:escapeXml(serie.getAirday())}</p>
    <p>Link: ${fn:escapeXml(serie.getLink())}</p>
    <p>Status: ${serie.getStatus()}</p>
    <p>Genre: ${serie.getGenre()}</p>

</c:if>

</body>
</html>
