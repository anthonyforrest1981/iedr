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
    <title>Buy Requests Search</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:set var="buyRequests" value="paginatedResult.list"/>

<s:form action="buy-requests-search" theme="simple">
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
                        <n:datefield2 field="searchCriteria.creationDateFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Date To" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.creationDateTo"/>
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
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Status" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:select list="statuses" name="searchCriteria.status"
                                theme="simple" cssStyle="width:150px;"
                                listValue="description"
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
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Checked Out To" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:select list="hostmasters" name="searchCriteria.checkedOutTo"
                                theme="simple" cssStyle="width:150px;"
                                listKey="username" listValue="username"
                                headerKey="" headerValue="[ALL]"/>
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
        <display:column title="Creation Date" sortable="true" sortProperty="buyCreationDate">
            <s:date name="#attr.buyRequestRow.creationDate"/>
        </display:column>
        <display:column title="Domain Name" property="domainName" sortable="true"/>
        <display:column title="Domain Holder" property="domainHolder" sortable="true"/>
        <display:column title="Account Name" property="account.name" sortable="true" sortProperty="accountName"/>
        <display:column title="Buyer's Name" property="adminName" sortable="true"/>
        <display:column title="Status" property="status.description" sortable="true" sortProperty="buyStatus"/>
        <display:column title="Checked Out To" property="checkedOutTo" sortable="true"/>

        <display:column title="">
            <secondarymarket:buyRequestActions buyRequest="${buyRequestRow}"/>
        </display:column>

    </display:table>

    <c:forEach items="${buyRequests}" var="buyRequestRow">
        <s:if test="allowCheckin(#attr.buyRequestRow)">
            <secondarymarket:checkin buyRequest="${buyRequestRow}" />
        </s:if>
        <s:if test="allowReassign(#attr.buyRequestRow)">
            <secondarymarket:reassign buyRequest="${buyRequestRow}" />
        </s:if>
    </c:forEach>
</n:group2>

</body>
</html>
