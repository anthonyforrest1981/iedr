<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sell Requests Search</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <s:head/>
    <sx:head/>
</head>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="sell-requests-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Request Id" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true" cssContainerStyle="padding-left:30%;"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.sellRequestId" theme="simple"
                                cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:34%">
                <div>
                    <n:field2 label="Creation Date From" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.createdFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.createdTo"/>
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
                    <n:field2 label="Completion Date From" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.completeFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.completeTo"/>
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
                    <n:field2 label="Account" labelJustify="right" tooltipGapHidden="true"
                            fieldEditable="true"
                            cssLabelStyle="float:left;width:40%;"
                            cssCtrlStyle="float:left;width:60%;">
                        <s:select list="accounts" name="searchCriteria.accountId"
                                theme="simple" cssStyle="width:150px;"
                                listKey="id" listValue="name"
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
    <display:table name="paginatedResult" id="sellRequestRow" class="result" requestURI=""
            sort="external" excludedParams="resetSearch">
        <display:column title="Request Id" property="id" sortable="true" sortProperty="sellId"/>
        <display:column title="Creation Date" sortable="true" sortProperty="sellCreationDate">
            <s:date name="#attr.sellRequestRow.creationDate"/>
        </display:column>
        <display:column title="Completion Date" sortable="true" sortProperty="sellCreationDate">
            <s:date name="calculateCompletionDate(#attr.sellRequestRow.creationDate)"/>
        </display:column>
        <display:column title="Domain Name" property="buyRequest.domainName" sortable="true"
            sortProperty="domainName"/>
        <display:column title="Domain Holder" property="buyRequest.domainHolder" sortable="true"
            sortProperty="domainHolder"/>
        <display:column title="Account Name" property="buyRequest.account.name" sortable="true"
            sortProperty="accountName"/>
        <display:column title="">
            <s:div cssClass="ticket-row-images">
                <s:url var="viewUrl" action="sell-request-view-view">
                    <s:param name="requestId">${sellRequestRow.id}</s:param>
                    <s:param name="previousAction">sell-requests-input</s:param>
                </s:url>
                <s:a href="%{viewUrl}">
                    <img src="images/skin-default/action-icons/details.png" alt="View" title="View"/>
                </s:a>
            </s:div>
        </display:column>

    </display:table>

</n:group2>

</body>
</html>