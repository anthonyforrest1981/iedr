<%@ attribute name="previousAction" required="true" %>
<%@ attribute name="selectedChangeId" type="java.lang.Long" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<n:group2 titleText="History">
    <display:table class="result" name="history" id="histRow" excludedParams="resetSearch">
        <c:set var="cssClass" value="${selectedChangeId == (histRow.changeId) ? 'selected' : ''}"/>
        <display:column title="Date" class="${cssClass}">
            <s:date name="#attr.histRow.object.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Nic Handle" property="changedBy" class="${cssClass}"/>
        <display:column title="Remark" property="object.hostmasterRemark" class="${cssClass}" escapeXml="true"/>
        <display:column class="${cssClass}">
            <s:url var="showHistoryUrl" action="buy-request-view-history" includeParams="none">
                <s:param name="buyRequestId">${histRow.object.id}</s:param>
                <s:param name="changeId">${histRow.changeId}</s:param>
                <s:param name="previousAction">${previousAction}</s:param>
            </s:url>
            <s:a href="%{showHistoryUrl}">
                <img src="images/skin-default/action-icons/ticket.png" alt="View Historical Record"
                     title="View Historical Record"/>
            </s:a>
        </display:column>
    </display:table>
</n:group2>
