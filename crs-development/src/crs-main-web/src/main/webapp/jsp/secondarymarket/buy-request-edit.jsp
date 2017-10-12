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
    <title>BuyRequest Edit</title>

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
    <secondarymarket:failableField label="Domain Holder"
                                    field="buyRequest.domainHolder"
                                    fieldEditable="true"
                                    reasonField="failures.domainHolderFR"
                                    reasonEditable="true" />
    <div class="FC force-height-double">
        <div class="FCL default-label-width">
            <div class="FCB-box tooltip-gap">
                <div class="FCL-value textOnly">Class</div>
            </div>
            <div class="ctrl-tooltip">&nbsp;</div>
            <div class="FCB-box tooltip-gap">
                <div class="FCL-value textOnly">Category</div>
            </div>
        </div>
        <div class="FCB default-label-gap">
            <div class="FCB-reason force-height-double">
                <div class="FCR-value">
                    <s:select list="reasonsList" headerKey="null" headerValue="(none)"
                              listValue="description" theme="simple">
                        <s:param name="name">failures.holderClassFR</s:param>
                        <s:param name="value">${failures.holderClassFR}</s:param>
                    </s:select>
                </div>
                <div class="FCR-value">
                    <s:select list="reasonsList" headerKey="null" headerValue="(none)"
                              listValue="description" theme="simple" cssStyle="margin-top:5px">
                        <s:param name="name">failures.holderCategoryFR</s:param>
                        <s:param name="value">${failures.holderCategoryFR}</s:param>
                    </s:select>
                </div>
            </div>
            <div class="FCB-box reason-gap">
                <div class="FCC-box selector-gap">
                    <div class="FCC-value">
                        <s:doubleselect
                                id="list1"
                                name="buyRequest.holderClass.id"
                                list="classes"
                                listKey="id"
                                listValue="name"
                                label="Class"
                                doubleCssStyle="margin-top:5px"
                                doubleId="list2"
                                doubleName="buyRequest.holderCategory.id"
                                doubleList="categories"
                                doubleListKey="id"
                                doubleListValue="name"
                                disabled="false"
                                theme="simple"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</n:group2>

</td>

<td width="50%">
    <n:group2 titleText="New Admin" cssIcon="group-icon-contact">
        <secondarymarket:failableField label="Name"
                                       field="buyRequest.adminName"
                                       fieldEditable="true"
                                       reasonField="failures.adminNameFR"
                                       reasonEditable="true" />
        <secondarymarket:failableField label="Email"
                                       field="buyRequest.adminEmail"
                                       fieldEditable="true"
                                       reasonField="failures.adminEmailFR"
                                       reasonEditable="true"
                                       idnTooltip="true"
                                       idnTooltipType="Email" />
        <secondarymarket:failableField label="Company Name"
                                       field="failures.adminCompanyName"
                                       fieldEditable="true"
                                       reasonField="failures.adminCompanyNameFR"
                                       reasonEditable="true" />
        <secondarymarket:failableField label="Address"
                                       field="buyRequest.adminAddress"
                                       fieldEditable="true"
                                       reasonField="failures.adminAddressFR"
                                       reasonEditable="true"
                                       multiline="true" />
        <secondarymarket:country countryList="countries"
                      countryField="buyRequest.adminCountry.id"
                      countyField="buyRequest.adminCounty.id"/>
        <secondarymarket:telecomlist label="Phone" inputDisabled="false" wrapper="phonesWrapper"
                                     reasonField="failures.phonesFR"
                                     reasonEditable="true"
                                     reasonHidden="false" />
        <secondarymarket:telecomlist label="Fax" inputDisabled="false" wrapper="faxesWrapper"
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
                            <s:if test="%{hasPermission('buyRequest.edit')}">
                                <s:submit value="Save" method="save" theme="simple"/>
                            </s:if>
                            <s:submit value="Cancel" method="cancel" theme="simple"/>
                        </center>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <secondarymarket:history previousAction="buy-request-edit"/>
        </td>
    </tr>
</table>
</s:form>

</body>
</html>
