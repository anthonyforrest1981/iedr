<%@ attribute name="to" required="true" type="java.util.List" %>
<%@ attribute name="cc" required="true" type="java.util.List" %>
<%@ attribute name="bcc" required="true" type="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="htmlutils" uri="htmlutils" %>

<c:if test="${ ! empty to}">
    to: ${htmlutils:join(to, ", ")}<br/>
</c:if>
<c:if test="${ ! empty cc}">
    cc: ${htmlutils:join(cc, ", ")}<br/>
</c:if>
<c:if test="${ ! empty bcc}">
    bcc: ${htmlutils:join(bcc, ", ")}
</c:if>