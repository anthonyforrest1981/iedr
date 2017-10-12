<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Domains with Locking Service</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<s:actionerror/>
<s:actionmessage/>

<n:group2 titleText="Criteria" cssIcon="group-icon-search">
<s:form action="domainsRollLockingService-search" theme="simple" name="domainsSearch">
    <div style="padding:5px 0 0 0; height:26px;">
        <div style="float:left; width:33%">
            <n:field2 label="Locking Renewal Date" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
                         cssCtrlStyle="float:left;width:60%;">
                <n:datefield2 field="searchCriteria.lockRenewalFrom"/>
            </n:field2>
        </div>
        <div style="float:left; width:34%">
            <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:20%;" cssCtrlStyle="float:left;width:42%;">
                <n:datefield2 field="searchCriteria.lockRenewalTo"/>
            </n:field2>
        </div>
        <div style="float:left; width:33%">
            <n:field2 label="Account" labelJustify="right" tooltipGapHidden="true" fieldEditable="true"
                         cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
                <s:select name="searchCriteria.accountId" list="accounts" listKey="id" listValue="name"
                         headerKey="-1" headerValue="[ALL]" theme="simple" cssStyle="width:150px;"/>
            </n:field2>
        </div>
    </div>

    <div style="clear:both;">
        <n:refreshsearch1/>
    </div>
</s:form>
</n:group2>

<s:form action="domainsRollLockingService-confirmRollDates.action" theme="simple">
<n:group2 titleText="Domains to prolong locking service" cssIcon="group-icon-search">
<div class="checkbox-list-container">
    <center>
        <input type="button" value="Clear" class="clear-button" />
        <s:submit value="Prolong" cssClass="action-button" />
    </center>
</div>
</n:group2>
</s:form>

<n:group2 titleText="Results" cssIcon="group-icon-domain">
    <display:table name="paginatedResult" id="domainRow" class="result" requestURI="" sort="external" excludedParams="resetSearch" export="true" decorator="pl.nask.crs.web.displaytag.RollLockingServiceTableDecorator">
        <display:column title="" headerClass="checkbox-list-selectall-placeholder" media="html">
            <input class="print-checkbox" checkbox-list-value="${domainRow.name}" type="checkbox">
        </display:column>
        <display:column title="Domain Name" property="name" sortable="true"/>
        <display:column title="BillC NH" property="billingContact.nicHandle" sortable="false" sortProperty="billingNH"/>
        <display:column title="BillC Name" property="billingContact.name" sortable="false"/>
        <display:column title="Locked" property="dsmState.locked" sortable="false" decorator="pl.nask.crs.web.displaytag.BooleanDecorator"/>
        <display:column title="Locking Service Date" sortable="true" sortProperty="lockingDate">
            <s:date name="#attr.domainRow.lockingDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Locking Service Renewal Date" sortable="true" sortProperty="lockingRenewalDate">
            <s:date name="#attr.domainRow.lockingRenewalDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="" media="html">
            <s:div cssClass="ticket-row-images">
                <s:url var="viewUrl" action="domainview" includeParams="none">
                    <s:param name="domainName">${domainRow.name}</s:param>
                    <s:param name="previousAction">domainsRollLockingService-search</s:param>
                </s:url>
                <s:a href="%{viewUrl}">
                    <img src="images/skin-default/action-icons/domain.png" alt="View" title="View"/>
                </s:a>
            </s:div>
        </display:column>
    </display:table>
</n:group2>

<script type="text/javascript" src="js/server-stored-list.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var addHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "domainsRollLockingService-addToStorage.action",
            data : {selectedDomains : values},
            complete : callback
        });
    };
    var removeHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "domainsRollLockingService-removeFromStorage.action",
            data : {selectedDomains : values},
            complete : callback
        });
    };
    var selectedDomains = $.grep("${allSelectedAsString}".split(","), function(e) { return e; } );

    $.CRS.serverStoredLists.checkboxList({
            listContainerClass: "checkbox-list-container",
            linksClass: "print-checkbox",
            linksValueAttribute: "checkbox-list-value",
            selectedValues: selectedDomains,
            addHandler: addHandler,
            removeHandler: removeHandler,
            selectAllPlaceholderClass: "checkbox-list-selectall-placeholder",
            getCountMessage: function(listLength) {
                switch (listLength) {
                    case 0:
                        return "You haven't selected any domains to roll locking service renewal date"
                    case 1:
                        return "You have selected 1 domain"
                    default:
                        return "You have selected " + listLength + " domains"
                }
            },
            maxSelected: 500,
            getMaxSelectedMessage: function(toSelectLength, selectedLength, limit) {
                return "Selecting " + (toSelectLength === 1 ? "this domain" : "these domains")
                    + " would exceed the limit (" + limit + ")";
            }
        });
});
</script>
</body>
</html>
