<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib prefix="nask" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="permission" tagdir="/WEB-INF/tags/permission" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Nic Handle Edit </title>

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
            $('#contact-info-group [data-idntooltip-field="true"]').each(function() {
                $(this).idnTooltip({
                    iconContainer: function(element) {
                        return $(".ctrl-tooltip", element.parents(".ctrl-container"));
                    },
                    type: $(this).attr("data-idntooltip-type")
                });
            });
        })
    </script>
    <s:if test="%{isHistory()}">
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css"
              media="all"/>
    </s:if>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="nic_handle_form" theme="simple">
<s:hidden name="nicHandleId"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td width="50%">
            <n:group2 titleText="Nic Handle" cssIcon="group-icon-nichandle">
                <n:field label="Nic Handle Id">
                    <div class="showValue">${wrapper.nicHandleId}</div>
                </n:field>
                <n:field label="Nic Handle Name" fielderror="wrapper.name">
                    <input type="text" name="wrapper.name" value="${wrapper.name}"/>
                </n:field>
                <n:field label="Company Name" fielderror="wrapper.companyName">
                    <input type="text" name="wrapper.companyName" value="${wrapper.companyName}"/>
                </n:field>
                <n:field label="Account" fielderror="accountNumberWrapper.accountNumber">
                    <s:select name="accountNumberWrapper.accountNumber" list="accounts" theme="simple" listKey="id"
                              listValue="name"
                              headerValue="[UNKNOWN]"/>
                </n:field>
                <n:field label="Domains">
                    <domain:showdomains contact="${wrapper.nicHandleId}" />
                </n:field>
            </n:group2>

            <n:group2 titleText="VAT" cssIcon="group-icon-cash">
                <n:field label="VAT No" fielderror="wrapper.vatNo">
                    <input type="text" name="wrapper.vatNo" value="${wrapper.vatNo}"/>
                </n:field>
                <n:field label="VAT Category" >
                    <s:select list="categories" name="wrapper.vatCategory" headerKey="" headerValue="(unset)" theme="simple" />
                </n:field>
            </n:group2>
        </td>
        <td width="50%">
            <n:group2 titleText="Status" cssIcon="group-icon-status">
                <n:field label="Changed">
                    <div class="showValue"><s:date name="wrapper.changeDate"/></div>
                </n:field>
                <n:field label="Status">
                    <div class="showValue">${wrapper.status}</div>
                </n:field>
            </n:group2>
            <n:group2 id="contact-info-group" titleText="Contact" cssIcon="group-icon-contact">
                <n:field label="E-mail" fielderror="wrapper.email">
                    <input type="text" name="wrapper.email" value="${wrapper.email}"
                         data-idntooltip-field="true" data-idntooltip-type="Email"/>
                </n:field>
                <div style="height:108px;">
                    <n:field label="Address" fielderror="wrapper.address">
                        <s:textarea name="wrapper.address" theme="simple"/>
                    </n:field>
                </div>
                <nask:country countryList="countries"
                              countryField="wrapper.countryId"
                              countyField="wrapper.countyId"/>
                <nichandle:telecomlist label="Phone" inputDisabled="false" wrapper="wrapper.phonesWrapper"/>
                <nichandle:telecomlist label="Fax" inputDisabled="false" wrapper="wrapper.faxesWrapper"/>
            </n:group2>
            <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
                <s:textarea name="hostmastersRemark" theme="simple"></s:textarea>
                <n:fielderror fields="hostmastersRemark"/>
            </n:group2>
        </td>
    </tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:submit value="Save" action="nic-handle-edit" method="save" theme="simple" cssClass="xSave"/>
            </center>
        </td>
    </tr>
</table>
</s:form>
<s:form theme="simple">
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <center>
                <s:hidden name="nicHandleId"/>
                <s:submit value="Cancel" action="nic-handle-view" method="view" theme="simple" cssClass="xCancel"/>
            </center>
        </td>
    </tr>
</table>
</s:form>

</body>
</html>