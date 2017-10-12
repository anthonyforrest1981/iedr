<%@ attribute name="tooltip" required="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<img src="images/skin-default/action-icons/info.png" data-tooltip-icon="true" data-tooltip-content="<c:out value="${tooltip}"/>"/>
