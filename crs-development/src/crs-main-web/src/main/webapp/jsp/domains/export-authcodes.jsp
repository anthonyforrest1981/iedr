<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<s:set var="domains" value="paginatedResult.list"/>

<html>
<head>
    <title>Export Authcodes</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>
<s:form action="domainExportAuthCodes-search" theme="simple">
<n:group2 titleText="Criteria" cssIcon="group-icon-search">

<div style="padding:5px 0; height:26px;">
    <div style="float:left; width:50%">
        <div>
            <n:field2 label="Domain name" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
                <s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
            </n:field2>
        </div>
    </div>
    <div style="float:left; width:50%">
        <div>
            <n:field2 label="Nic Handle" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
                <s:textfield name="searchCriteria.nicHandle" theme="simple" cssStyle="width:150px;text-indent:0;"/>
            </n:field2>
        </div>
    </div>
</div>
<div style="clear:both;">
    <n:refreshsearch/>
</div>
</n:group2>
</s:form>

<s:form action="domainExportAuthCodes-export" theme="simple">
<n:group2 titleText="Selected domains" cssIcon="group-icon-search">
<div class="bubble-list-container">
    <center>
        <input type="button" value="Clear" id="do_clear" class="clear-button" />
        <s:submit id="do_export_authcodes" value="Export" cssClass="action-button" />
    </center>
</div>
</n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Domain Name" property="name" paramId="domainName" sortable="true"/>
        <display:column title="BillC NH" property="billingContact.nicHandle" sortable="false"/>
        <display:column title="Authcode" property="authCode" sortable="false"/>
        <display:column title="" media="html">
            <input
                class="export-button"
                bubble-list-value="${attr.domainRow.name}"
                type="button"
                value="Add to export"
                style="font-size:1em; height:2em; width: 100px">
        </display:column>
    </display:table>
</n:group2>

<script type="text/javascript" src="js/server-stored-list.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var addHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "domainExportAuthCodes-addToStorage.action",
            data : {selectedDomains : values},
            complete : callback
        });
    };
    var removeHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "domainExportAuthCodes-removeFromStorage.action",
            data : {selectedDomains : values},
            complete : callback
        });
    };
    var selectedDomains = $.grep("${allSelectedAsString}".split(","), function(e) { return e; } );

    $.CRS.serverStoredLists.bubbleList({
        listContainerClass: "bubble-list-container",
        linksClass: "export-button",
        linksValueAttribute: "bubble-list-value",
        selectedValues: selectedDomains,
        addHandler: addHandler,
        removeHandler: removeHandler
    });
});
</script>
</body>
</html>