<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="secondarymarket" tagdir="/WEB-INF/tags/secondarymarket" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sale Authcodes Search</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<s:set var="statusList" value="statuses"/>
<s:set var="hostmasterList" value="hostmasters"/>
<s:set var="buyRequests" value="paginatedResult.list"/>

<s:form action="sale-authcodes-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Request Id" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true" cssContainerStyle="padding-left:30%;"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.buyRequestId" theme="simple"
                                cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:34%">
                <div>
                    <n:field2 label="Date From" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.authCodeCreationDateFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Date To" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.authCodeCreationDateTo"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true" cssContainerStyle="padding-left:30%;"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.domainName" theme="simple"
                                cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:34%">
                <div>
                    <n:field2 label="Account Name" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:select list="accounts" name="searchCriteria.sellerAccountId"
                                theme="simple" cssStyle="width:150px;"
                                listKey="id" listValue="name"
                                headerKey="" headerValue="[ALL]"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Domain Holder" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true" cssContainerStyle="padding-left:30%;"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.domainHolder" theme="simple"
                                cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:34%">
                <div>
                    <n:field2 label="Buyer's Name" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.buyerName" theme="simple"
                                cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="clear:both;">
            <n:refreshsearch/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Results">
    <display:table name="paginatedResult" id="buyRequestRow" class="result" requestURI=""
            sort="external" excludedParams="resetSearch">
        <display:column title="Request Id" property="id" sortable="true" sortProperty="buyId"/>
        <display:column title="Creation Date" sortable="true" sortProperty="authcodeDate">
            <s:date name="#attr.buyRequestRow.authcodeCreationDate"/>
        </display:column>
        <display:column title="Expiration Date" sortable="true" sortProperty="authcodeDate">
           <s:date format="dd/MM/yyyy" name="authcodeExpirationDate(#attr.buyRequestRow.authcodeCreationDate)"/>
        </display:column>
        <display:column title="Domain Name" property="domainName" sortable="true"/>
        <display:column title="Domain Holder" property="domainHolder" sortable="true"/>
        <display:column title="Account Name" property="account.name" sortable="true" sortProperty="accountName"/>
        <display:column title="Buyer's Name" property="adminName" sortable="true"/>
        <display:column title="">
            <div class="ticket-row-images">
                <s:url var="invalidateUrl" action="sale-authcodes-invalidate" includeParams="none">
                    <s:param name="id">${buyRequestRow.id}</s:param>
                </s:url>
                <s:url var="resendUrl" action="sale-authcodes-resend" includeParams="none">
                    <s:param name="id">${buyRequestRow.id}</s:param>
                </s:url>
                <s:if test="allowInvalidate(#attr.buyRequestRow) && hasPermission('saleAuthcode.invalidate')">
                    <s:a theme="simple" href="%{invalidateUrl}" onclick="return confirm('A removal of an authcode cannot be undone. Do you want to continue?');">
                        <img src="images/skin-default/action-icons/delete.png" alt="Delete" title="Delete"/>
                    </s:a>
                </s:if>
                <s:if test="hasPermission('saleAuthcode.resend')">
                    <s:a href="%{resendUrl}">
                        <img src="images/skin-default/action-icons/contact.default.png" alt="Regenerate and Resend Authcode" title="Regenerate and Resend Authcode" />
                    </s:a>
                </s:if>
            </div>
        </display:column>
    </display:table>
</n:group2>

</body>
</html>
