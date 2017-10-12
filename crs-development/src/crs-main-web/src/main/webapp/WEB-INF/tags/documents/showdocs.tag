<%@attribute name="condition" required="true" type="java.lang.Boolean" description="if true, 'YES' is displayed and a link to documents is shown. If false, 'NO' is displayed"%>
<%@attribute name="domainName" required="false" description="domain name for which the link to the documents should be generated"%>
<%@attribute name="buyRequestId" required="false" description="buy request id for which the link to the document should be generated"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:if test="%{#attr.domainName != null}">
    <s:url var="listDocumentsUrl" action="documents-list" includeParams="none">
        <s:param name="documentSearchCriteria.domainName">${domainName}</s:param>
    </s:url>
</s:if>
<s:if test="%{#attr.buyRequestId != null}">
    <s:url var="listDocumentsUrl" action="documents-list" includeParams="none">
        <s:param name="documentSearchCriteria.buyRequestId">${buyRequestId}</s:param>
    </s:url>
</s:if>

<div class="showDocs">
    <s:if test="#attr.condition">YES</s:if>
    <s:else>NO</s:else>
</div>

<div class="showDocs-icon">
<s:if test="#attr.condition && hasPermission('searchDocuments')">
    <s:a href="%{listDocumentsUrl}">
        <img src="images/skin-default/action-icons/details.png" title="Show" />
    </s:a>
</s:if>
</div>
