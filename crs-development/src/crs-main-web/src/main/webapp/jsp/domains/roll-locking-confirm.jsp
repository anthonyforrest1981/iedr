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

<s:form name="domain_locking_service_prolong" theme="simple" method="POST">
    <n:group2 titleText="Domains selected for prolonging locking service" cssIcon="group-icon-domain">
        <display:table name="domainsToRoll" id="domainRow" class="result" requestURI="" sort="external" excludedParams="resetSearch" export="true">
            <s:set var="newLockingDateUI"><s:date name="#attr.domainRow.newLockingRenewalDate" format="dd/MM/yyyy" /></s:set>
            <s:set var="newLockingDate"><s:date name="#attr.domainRow.newLockingRenewalDate" format="yyyy-MM-dd'T00:00:00'" /></s:set>
            <s:set var="renewalDateUI"><s:date name="#attr.domainRow.renewalDate" format="dd/MM/yyyy" /></s:set>
            <display:column title="Domain Name" property="name" sortable="false"/>
            <display:column title="BillC NH" property="billingNH" sortable="false" sortProperty="billingNH"/>
            <display:column title="BillC Name" property="billingName" sortable="false"/>
            <display:column title="Renewal date" sortable="false">
                <s:date name="#attr.domainRow.renewalDate" format="dd/MM/yyyy"/>
            </display:column>
            <display:column title="Locked" property="locked" sortable="false" decorator="pl.nask.crs.web.displaytag.BooleanDecorator"/>
            <display:column title="Locking Service Date" sortable="false">
                <s:date name="#attr.domainRow.lockingDate" format="dd/MM/yyyy"/>
            </display:column>
            <display:column title="Current Locking Service Renewal Date" sortable="false">
                <s:date name="#attr.domainRow.lockingRenewalDate" format="dd/MM/yyyy"/>
            </display:column>
            <display:column title="" sortable="false" headerClass="syncColumn">
                <div class="lockingDateSelector lockrenewaldate-1yr" attr-dateOneYear="${newLockingDateUI}" attr-dateSync="${renewalDateUI}">
                    <div class="lockrenewaldate-1yr-select">1 year</div>
                    <div class="lockrenewaldate-sync-select">Sync</div>
                </div>
            </display:column>
            <display:column title="New Locking Service Date" sortable="false">
                <input type="text" disabled="true" value="${newLockingDateUI}" />
                <input type="hidden" value="${newLockingDate}" name="domainsToRoll[${domainRow.index}].newLockingRenewalDate" />
            </display:column>
        </display:table>
    </n:group2>

    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                        <s:submit value="Proceed" action="domainsRollLockingService-doRollDates" theme="simple"/>
                        <s:submit value="Cancel" action="domainsRollLockingService-search" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>

<script type="application/javascript">
$(function() {
    $(".lockingDateSelector").click(function(e) {
        e.preventDefault();
        var $this = $(this);
        var newValue = null;
        if ($this.hasClass("lockrenewaldate-sync")) {
            $this.addClass("lockrenewaldate-1yr").removeClass("lockrenewaldate-sync");
            newValue = $this.attr("attr-dateOneYear");
        } else {
            $this.addClass("lockrenewaldate-sync").removeClass("lockrenewaldate-1yr");
            newValue = $this.attr("attr-dateSync");
        }
        $this.parent().parent().find('input[type=text]').val(newValue);
        var date = Date.parseDate(newValue, "%d/%m/%Y");
        $this.parent().parent().find('input[type=hidden]').val(date.print("%Y-%m-%dT00:00:00"));
    });
});
</script>

</body>
</html>
