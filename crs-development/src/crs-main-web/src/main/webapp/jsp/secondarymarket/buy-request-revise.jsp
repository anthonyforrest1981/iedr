<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/accounts" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="secondarymarket" tagdir="/WEB-INF/tags/secondarymarket" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="htmlutils" uri="htmlutils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>BuyRequest Revise</title>

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
            $("[data-idntooltip-field='true']").each(function () {
                $(this).idnTooltip({
                    iconContainer: function (element) {
                        return $(".FCL-icon", element.parents(".FC"));
                    },
                    type: $(this).attr("data-idntooltip-type")
                });
            });
        });
    </script>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error" displayActionErrors="false">
    <div style="max-height: 150px; overflow:auto">
        <s:actionerror escape="false"/>
    </div>
</n:error2>

<s:actionmessage />

<s:form name="buy_request_form" theme="simple">
<n:error2 cssIcon="group-icon-error" titleText="Warning" displayActionErrors="false" customErrorCondition="${forceEdit}">
    <p>This buy request has been restricted from editing as the Registrar has not signed Exhibit 3 of the Registrar Agreement.</p>

    <p>Are you sure you want to edit this buy request?
        <s:submit action="buy-request-edit" theme="simple" value="YES"/>
        <s:submit value="NO" method="cancelEdit" theme="simple"/>
    </p>
</n:error2>
<s:hidden name="buyRequestId"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="50%">
<n:group2 titleText="BuyRequest Status" cssIcon="group-icon-status">
    <n:field2 label="Id">${buyRequest.id}</n:field2>
    <n:field2 label="Created by">${buyRequest.creatorNH}</n:field2>
    <n:field2 label="Created"><s:date name="buyRequest.creationDate"/></n:field2>
    <n:field2 label="Changed"><s:date name="buyRequest.changeDate"/></n:field2>
    <n:field2 label="Expiry date"><s:date format="dd/MM/yyyy" name="requestExpirationDate"/></n:field2>
    <n:field2 label="Status">${buyRequest.status.description}</n:field2>
    <n:field2 label="Documentation">
        <docs:showdocs buyRequestId="${buyRequest.id}" condition="${hasDocuments}" />
    </n:field2>
</n:group2>
<n:group2 titleText="Domain" cssIcon="group-icon-domain">
    <secondarymarket:failableField label="Domain Name"
                                    field="buyRequest.domainName"
                                    fieldEditable="false"
                                    reasonField="failures.domainNameFR"
                                    reasonEditable="true"
                                    idnTooltip="true"
                                    idnTooltipType="Domain" />
    <n:field2 label="Account Name">${buyRequest.account.name}</n:field2>
    <n:field2 label="Agreement Signed"><n:showBoolean value="${buyRequest.account.agreementSigned}" /></n:field2>
    <n:field2 label="Edit Ticket"><n:showBoolean value="${buyRequest.account.ticketEdit}" /></n:field2>
    <c:set var="relatedDomains" value="${htmlutils:displayListAsTable(relatedDomainNames, 3, null)}${htmlutils:displayListAsTable(pendingDomainNames, 3, \"pending-domain-names\")}"/>
    <secondarymarket:failableField label="Domain Holder"
                                    field="buyRequest.domainHolder"
                                    fieldEditable="false"
                                    tooltip="${relatedDomains}"
                                    reasonField="failures.domainHolderFR"
                                    reasonEditable="true" />
    <secondarymarket:failableField label="Class"
                                    field="buyRequest.holderClass.name"
                                    fieldEditable="false"
                                    reasonField="failures.holderClassFR"
                                    reasonEditable="true" />
    <secondarymarket:failableField label="Category"
                                    field="buyRequest.holderCategory.name"
                                    fieldEditable="false"
                                    reasonField="failures.holderCategoryFR"
                                    reasonEditable="true" />
</n:group2>

</td>

<td width="50%">
    <n:group2 titleText="New Admin" cssIcon="group-icon-contact">
        <secondarymarket:failableField label="Name"
                                       field="buyRequest.adminName"
                                       fieldEditable="false"
                                       reasonField="failures.adminNameFR"
                                       reasonEditable="true" />
        <secondarymarket:failableField label="Email"
                                       field="buyRequest.adminEmail"
                                       fieldEditable="false"
                                       reasonField="failures.adminEmailFR"
                                       reasonEditable="true"
                                       idnTooltip="true"
                                       idnTooltipType="Email" />
        <secondarymarket:failableField label="Company Name"
                                       field="buyRequest.adminCompanyName"
                                       fieldEditable="false"
                                       reasonField="failures.adminCompanyNameFR"
                                       reasonEditable="true" />
        <secondarymarket:failableField label="Address"
                                       field="buyRequest.adminAddress"
                                       fieldEditable="false"
                                       reasonField="failures.adminAddressFR"
                                       reasonEditable="true"
                                       multiline="true" />
        <secondarymarket:failableField label="Country"
                                       field="buyRequest.adminCountry.name"
                                       fieldEditable="false"
                                       reasonField="failures.adminCountryFR"
                                       reasonEditable="true" />
        <secondarymarket:failableField label="County"
                                       field="buyRequest.adminCounty.name"
                                       fieldEditable="false"
                                       reasonField="failures.adminCountyFR"
                                       reasonEditable="true" />
        <secondarymarket:telecomlist label="Phone" inputDisabled="true" wrapper="phonesWrapper"
                                     reasonField="failures.phonesFR"
                                     reasonEditable="true"
                                     reasonHidden="false" />
        <secondarymarket:telecomlist label="Fax" inputDisabled="true" wrapper="faxesWrapper"
                                     reasonField="failures.faxesFR"
                                     reasonEditable="true"
                                     reasonHidden="false" />
    </n:group2>
    <n:group2 titleText="Requester's Remark" cssIcon="group-icon-remarks">
        <s:if test="isEmpty(buyRequest.remark)">
            (none)
        </s:if>
        <s:else>
            <div style="white-space:pre-wrap;"><c:out value="${buyRequest.remark}"/></div>
        </s:else>
    </n:group2>
    <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
        <ticket:hostmasterRemark />
        <n:fielderror fields="responseText" colspan="1"></n:fielderror>
    </n:group2>
</td>
</tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
                <tr>
                    <td>
                        <center>
                            <s:if test="%{hasPermission('buyRequest.accept')}">
                                <s:submit value="Accept" method="accept" theme="simple"/>
                            </s:if>
                        </center>
                    </td>
                    <td width="76%">
                        <center>
                            <s:if test="%{hasPermission('buyRequest.save')}">
                                <s:submit value="Save" method="save" theme="simple"/>
                            </s:if>
                            <s:if test="%{hasPermission('buyRequest.reject')}">
                                <img src="images/skin-default/web-parts/spacer.png" alt="" width="5%" height="1"/>
                                <input type="button" value="Reject" id="openRejectDialog"/>
                            </s:if>
                            <s:if test="%{buyRequest.account.ticketEdit && hasPermission('buyRequest.edit')}">
                                <img src="images/skin-default/web-parts/spacer.png" alt="" width="5%" height="1"/>
                                <s:submit value="Edit" action="buy-request-edit" theme="simple"/>
                            </s:if>
                            <s:if test="%{!buyRequest.account.ticketEdit && hasPermission('buyRequest.edit')}">
                                <img src="images/skin-default/web-parts/spacer.png"alt=""width="5%"height="1"/>
                                <s:checkbox name="forceEdit" theme="simple" onchange="submit();">
                                    <label>Bypass edit flags</label>
                                </s:checkbox>
                            </s:if>
                        </center>
                    </td>
                    <td>
                        <center>
                            <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                        </center>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <secondarymarket:history previousAction="buy-request-revise-input"/>
        </td>
    </tr>
</table>
<secondarymarket:reject buyRequest="${buyRequest}" />
</s:form>

</body>
</html>
