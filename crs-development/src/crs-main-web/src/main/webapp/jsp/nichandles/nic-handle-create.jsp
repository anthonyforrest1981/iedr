<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Nic Handle Create</title>

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
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </s:if>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="nic_handle_form" theme="simple">
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Nic Handle" cssIcon="group-icon-nichandle">
                    <n:field label="Nic Handle Name"><input type="text" name="nicHandleWrapper.name"
                                                            value="${nicHandleWrapper.name}"/></n:field>
                    <n:fielderror fields="nicHandleWrapper.name" colspan="1"/>
                    <n:field label="Company Name"><input type="text" name="nicHandleWrapper.companyName"
                                                         value="${nicHandleWrapper.companyName}"/></n:field>
                    <n:field label="Account">
                        <s:select name="nicHandleWrapper.accountNumber" list="accounts" theme="simple"
                                  listKey="id" listValue="name"/>
                    </n:field>
                    <n:fielderror fields="nicHandleWrapper.accountNumber" colspan="1"/>
                </n:group2>
                <n:group2 titleText="VAT" cssIcon="group-icon-cash">
                    <n:field label="VAT No"><input type="text" name="nicHandleWrapper.vatNo"
                                                   value="${nicHandleWrapper.vatNo}"/></n:field>
                    <n:fielderror fields="nicHandleWrapper.vatNo" colspan="1"/>
                </n:group2>
            </td>
            <td width="50%">
                <n:group2 id="contact-info-group" titleText="Contact" cssIcon="group-icon-contact">
                    <n:field label="E-mail">
                        <input type="text" name="nicHandleWrapper.email" value="${nicHandleWrapper.email}"
                            data-idntooltip-field="true" data-idntooltip-type="Email"/>
                    </n:field>
                    <n:fielderror fields="nicHandleWrapper.email" colspan="1"/>
                    <div style="height:108px;">
                        <n:field label="Address">
                            <s:textarea name="nicHandleWrapper.address" theme="simple"/>
                        </n:field>
                    </div>
                    <n:fielderror fields="nicHandleWrapper.address" colspan="1"/>
                    <n:country countryList="countries"
                        countryField="nicHandleWrapper.countryId"
                        countyField="nicHandleWrapper.countyId"/>
                    <nichandle:telecomlist label="Phone" inputDisabled="false" wrapper="nicHandleWrapper.phonesWrapper" />
                    <nichandle:telecomlist label="Fax" inputDisabled="false" wrapper="nicHandleWrapper.faxesWrapper" />
                </n:group2>
                <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
                    <s:textarea name="hostmastersRemark" theme="simple"></s:textarea>
                    <n:fielderror fields="hostmastersRemark" colspan="1"/>
                </n:group2>
            </td>
        </tr>
    </table>
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:submit value="Save" action="nic-handle-create" method="create" theme="simple" cssClass="xSave"/>
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
                    <s:submit value="Cancel" action="%{previousAction!=null?previousAction:'nic-handles-search'}" theme="simple" cssClass="xCancel"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>

</body>
</html>