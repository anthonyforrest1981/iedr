<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>

<html>
<head>
    <title>Sell Request View<s:if test="%{isHistory()}"> (History)</s:if></title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <s:head/>
    <sx:head/>

    <s:if test="%{isHistory()}">
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </s:if>

    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript" src="js/punycode.min.js"></script>
    <script type="text/javascript" src="js/idn-tools.js"></script>
    <script type="text/javascript" src="js/idn-tooltip.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("[data-idntooltip-field='true']").each(function () {
                $(this).idnTooltip({
                    iconContainer: function (element) {
                        return $(".ctrl-tooltip", element.parents(".ctrl-container"));
                    },
                    type: $(this).attr("data-idntooltip-type")
                });
            });
        });
    </script>
</head>

<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="sell_request_form" theme="simple">
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Status">
                    <n:field label="Id"><div class="showValue">${sellRequest.id}</div></n:field>
                    <n:field label="Creation Date">
                        <div class="showValue"><s:date name="sellRequest.creationDate"/></div>
                    </n:field>
                    <n:field label="Completion Date">
                        <div class="showValue"><s:date format="dd/MM/yyyy" name="completionDate"/></div>
                    </n:field>
                    <n:field label="Expiration Date">
                        <div class="showValue"><s:date format="dd/MM/yyyy" name="expirationDate"/></div>
                    </n:field>
                </n:group2>
                <n:group2 titleText="Buy Request Details">
                    <n:field label="Domain Name">
                        <div class="showValue" data-idntooltip-field="true"
                            data-idntooltip-type="Domain">${sellRequest.buyRequest.domainName}</div>
                    </n:field>
                    <n:field label="Domain Holder">
                        <div class="showValue">${sellRequest.buyRequest.domainHolder}</div>
                    </n:field>
                    <n:field label="Class">
                        <div class="showValue">${sellRequest.buyRequest.holderClass.name}</div>
                    </n:field>
                    <n:field label="Category">
                        <div class="showValue">${sellRequest.buyRequest.holderCategory.name}</div>
                    </n:field>
                </n:group2>
            </td>
            <td width="50%">
                <n:group2 titleText="Buyer's Details">
                    <n:field label="Name">
                        <div class="showValue">${sellRequest.buyRequest.adminName}</div>
                    </n:field>
                    <n:field label="Company Name">
                        <div class="showValue">${sellRequest.buyRequest.adminCompanyName}</div>
                    </n:field>
                    <n:field label="Email">
                        <div class="showValue" data-idntooltip-field="true"
                            data-idntooltip-type="Email">${sellRequest.buyRequest.adminEmail}</div>
                    </n:field>
                    <n:field label="Address">
                        <div class="showValue">${sellRequest.buyRequest.adminAddress}</div>
                    </n:field>
                    <n:field label="County">
                        <div class="showValue">${sellRequest.buyRequest.adminCounty.name}</div>
                    </n:field>
                    <n:field label="Country">
                        <div class="showValue">${sellRequest.buyRequest.adminCountry.name}</div>
                    </n:field>
                    <nichandle:telecomlist label="Phone" inputDisabled="true" wrapper="phonesWrapper"/>
                    <nichandle:telecomlist label="Fax" inputDisabled="true" wrapper="faxesWrapper"/>
                </n:group2>
            </td>
        </tr>
    </table>
    <hr class="buttons"/>
    <center>
        <s:if test="%{!isHistory() && hasPermission('sellRequest.cancel')}">
            <s:submit value="Cancel Process" theme="simple" action="sell-request-view-cancelProcess"
                      onclick="return confirm('Are you sure you want to cancel the sell request?');" />
        </s:if>
        <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
    </center>

</s:form>

</body>
</html>