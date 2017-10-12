<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="htmlutils" uri="htmlutils" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Domain View
        <s:if test="%{isHistory()}">(History)</s:if>
    </title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript" src="js/punycode.min.js"></script>
    <script type="text/javascript" src="js/idn-tools.js"></script>
    <script type="text/javascript" src="js/idn-tooltip.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
            $('#domain-info-group [data-idntooltip-field="true"]').each(function () {
                $(this).idnTooltip({
                    iconContainer: function (element) {
                        return $(".ctrl-tooltip", element.parents(".ctrl-container"));
                    },
                    type: $(this).attr("data-idntooltip-type")
                });
            });;
        })
    </script>
    <s:if test="%{isHistory()}">
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </s:if>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error" displayActionErrors="false">
    <div style="max-height: 150px; overflow:auto">
        <s:actionerror escape="false"/>
    </div>
</n:error2>
<s:actionmessage/>

<s:form name="domain_form" theme="simple" method="POST">
<s:hidden name="id"/>
<s:hidden name="domainName" value="%{#attr.domainWrapper.name}"/>
<%--<s:hidden name="previousAction" value="%{#attr.previousAction}"/>--%>
<s:hidden name="page" value="1"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="50%">
    <n:group2 id="domain-info-group" titleText="Domain" cssIcon="group-icon-domain">
        <n:field label="Domain Name">
            <div class="showValue" data-idntooltip-field="true" data-idntooltip-type="Domain">${domainWrapper.name}</div>
        </n:field>
        <s:if test="%{isHistory()}">
            <c:set var="relatedDomains" value=""/>
        </s:if>
        <s:else>
            <c:set var="relatedDomains" value="${htmlutils:displayListAsTable(domainWrapper.relatedDomainNames, 3, null)}${htmlutils:displayListAsTable(domainWrapper.pendingDomainNames, 3, \"pending-domain-names\")}"/>
        </s:else>
        <n:field label="Domain Holder" tooltip="${relatedDomains}">
            <div class="showValue">${domainWrapper.domainHolder}</div>
        </n:field>
        <n:field label="Account">
            <div class="showValue">${domainWrapper.resellerAccount.name}</div>
        </n:field>
        <n:field label="Class">
            <div class="showValue">${domainWrapper.holderClass.name}</div>
        </n:field>
        <n:field label="Category">
            <div class="showValue">${domainWrapper.holderCategory.name}</div>
        </n:field>
        <n:field label="Subcategory">
            <div class="showValue">${domainWrapper.holderSubcategory.name}</div>
        </n:field>
        <n:field label="Clik Paid">
            <s:checkbox name="domainWrapper.clikPaid" label="Clik Paid" disabled="true" theme="simple"/></n:field>
    </n:group2>

    <n:group2 titleText="Domain Dates">
        <n:field label="Changed">
            <div class="showValue"><s:date name="domainWrapper.changeDate"/></div>
        </n:field>
        <n:field label="Registration Date">
            <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.registrationDate"/></div>
        </n:field>
        <n:field label="Renewal Date">
            <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.renewalDate"/></div>
        </n:field>
        <s:if test="domainWrapper.nrp && domainWrapper.suspensionDate != null">
            <n:field label="Suspension Date">
                <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.suspensionDate"/></div>
            </n:field>
        </s:if>
        <s:if test="domainWrapper.nrp && domainWrapper.deletionDate != null">
            <n:field label="Deletion Date">
                <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.deletionDate"/></div>
            </n:field>
        </s:if>
        <s:if test="domainWrapper.lockingDate != null">
            <n:field label="Locking Service Date">
                <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.lockingDate"/></div>
            </n:field>
        </s:if>
        <s:if test="domainWrapper.lockingRenewalDate != null">
            <n:field label="Locking Service Renewal Date">
                <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.lockingRenewalDate"/></div>
            </n:field>
        </s:if>
    </n:group2>

    <n:group2 titleText="Domain Contacts" cssIcon="group-icon-contact">
        <s:set var="admin1Info"  value="makeContactInfo(domainWrapper.adminContacts[0])"/>
        <s:set var="admin2Info"  value="makeContactInfo(domainWrapper.adminContacts[1])"/>
        <s:set var="techInfo"  value="makeContactInfo(domainWrapper.techContacts[0])"/>
        <s:set var="billingInfo"  value="makeContactInfo(domainWrapper.billingContacts[0])"/>
        <n:field label="Admin Contact 1"
                 tooltip="${admin1Info}">
            <div class="showValue" id="testId1">
                    ${domainWrapper.adminContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.adminContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.adminContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Admin Contact 2"
                 tooltip="${admin2Info}">
            <div class="showValue" id="testId2">
                    ${domainWrapper.adminContacts[1].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.adminContacts[1].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.adminContacts[1].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Tech Contact"
                 tooltip="${techInfo}">
            <div class="showValue">
                    ${domainWrapper.techContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.techContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.techContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Billing Contact"
                 tooltip="${billingInfo}">
            <div class="showValue">
                    ${domainWrapper.billingContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.billingContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.billingContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
    </n:group2>

</td>
<td width="50%">
        <%--<n:group2 titleText="Domain Dates" cssIcon="group-icon-status">--%>

    <n:group2 titleText="Domain Status" cssIcon="group-icon-status">
        <s:if test="%{isHistory() == false}">
            <n:field label="Documentation">
                <docs:showdocs domainName="${domainWrapper.name}"
                               condition="${domainWrapper.documents}"></docs:showdocs>
            </n:field>
            <n:field label="Tickets">
                <s:checkbox name="domainWrapper.tickets" label="Tickets" disabled="true" theme="simple"/>
            </n:field>
        </s:if>
        <s:if test="%{domainWrapper.dsmState.valid}">
            <domain:dsmstatus dsmState="${domainWrapper.dsmState}" />
        </s:if>
        <s:else>
            <n:field label="Invalid DSM State">${domainWrapper.dsmState.id}</n:field>
        </s:else>
    </n:group2>
    <n:group2 titleText="DNS Name Servers" cssIcon="group-icon-dns">
        <domain:nameservers inputDisabled="true" wrapper="domainWrapper.dnsWrapper"/>
    </n:group2>
    <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
        <div style="white-space:pre-wrap;"><s:text name="domainWrapper.remark"/></div>
    </n:group2>
</td>
</tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:if test="%{isHistory()}">
                    <s:if test="%{hasCurrent()}">
                        <s:hidden name="previousAction"/>
                        <s:submit value="Current Domain" action="domainview" theme="simple"/>
                    </s:if>
                    <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                </s:if>
                <s:else>
                    <s:if test="%{hasPermission('domain.alterstatus.button')}">
                        <input type="button" value="Alter Status" id="openAlterStatusDialog"/>
                    </s:if>
                    <s:if test="%{hasPermission('domain.changeHolderType.button')}">
                        <input type="button" value="Change Holder Type" id="openChangeHolderTypeDialog"/>
                    </s:if>
                    <s:if test="%{isLocked()}">
                        <s:if test="%{hasPermission('domain.unlock.button')}">
                            <input type="button" value="Unlock" id="openChangeLockDialog"/>
                        </s:if>
                    </s:if>
                    <s:elseif test="%{isEditable()}">
                        <s:if test="%{hasPermission('domain.lock.button')}">
                            <input type="button" value="Lock" id="openChangeLockDialog"/>
                        </s:if>
                    </s:elseif >
                    <s:if test="%{hasPermission('domain.edit.button') && isEditable()}">
                        <s:submit value="Edit" action="domainedit" method="input" theme="simple"/>
                    </s:if>
                    <s:submit value="DNS Check" action="domainview-dnsCheck" theme="simple"/>
                    <s:if test="%{hasPermission('domain.sendAuthCode.button') && isEditable()}">
                        <s:submit value="Send Authcode" action="domainview-sendAuthCode" theme="simple"/>
                    </s:if>
                    <s:if test="%{hasPermission('domain.sendWhois.button')}">
                        <s:submit value="Send Whois Data" action="domainview-sendWhois" theme="simple"/>
                    </s:if>
                    <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                </s:else>
            </center>
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <domain:hdomainlist firstSearch="false" sortable="false"/>
        </td>
    </tr>
</table>
</s:form>

<s:if test="%{!isHistory()}">
    <domain:alterstatus domain="${domainWrapper}" domainStatuses="${domainStatuses}" formAction="domainalterstatus-alterStatus.action"/>
    <domain:changeholdertype formAction="domainChangeHolderType-updateHolderType.action" holderTypes="${holderTypes}" domain="${domainWrapper}" />
    <domain:prepareLockPopup domain="${domainWrapper}" isLocked="${locked}" alreadyInLockingService="${inLockingService}"/>
</s:if>

</body>
</html>
