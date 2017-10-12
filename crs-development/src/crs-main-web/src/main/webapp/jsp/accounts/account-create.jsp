<%@ page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Account Create</title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/jquery-update-value.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript" src="js/punycode.min.js"></script>
    <script type="text/javascript" src="js/idn-tools.js"></script>
    <script type="text/javascript" src="js/idn-tooltip.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                      .jqmAddTrigger($('#open-status-dialog'))
                      .jqmAddClose($('#close-status-dialog'));
            $('#billing-contact-info-group [data-idntooltip-field="true"]').each(function() {
                $(this).idnTooltip({
                    iconContainer: function(element) {
                        return $(".ctrl-tooltip", element.parents(".ctrl-container"));
                    },
                    type: $(this).attr("data-idntooltip-type")
                });
            });
        })
    </script>
    <script type="text/javascript">
        function selectNicHandle(nicHandle, email, cntrl) {
            var root = $(cntrl).parents('.jqmWindow');
            var fieldId1 = '#' + root.children('.fieldId')[0].value;
            var fieldId2 = '#' + root.children('.fieldEmail')[0].value;
            $(fieldId1).val(nicHandle);
            $(fieldId2).updateValue(email);
            root.jqmHide();
        }

        function changeTicketEditFlag(value, ticketEditCheckboxId) {
            el = document.getElementById(ticketEditCheckboxId);
            if (value.checked) {
                el.disabled = false;
            } else {
                el.disabled = true;
                el.checked = false;
            }
        }
    </script>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="account_form" theme="simple">
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Account" cssIcon="group-icon-account">
                    <n:field label="Account Name"><input type="text" name="accountCreateWrapper.name" value="${accountCreateWrapper.name}"/></n:field>
                    <n:fielderror fields="accountCreateWrapper.name" colspan="1"/>
                    <n:field label="Web Address"><input type="text" name="accountCreateWrapper.webAddress" value="${accountCreateWrapper.webAddress}"/></n:field>
                    <n:fielderror fields="accountCreateWrapper.webAddress" colspan="1"/>
                    <n:field label="Signed Agreement">
                        <s:checkbox name="accountCreateWrapper.agreementSigned" label="Signed Agreement" theme="simple" onchange="changeTicketEditFlag(this, 'ticketEditChkbx')"/>
                    </n:field>
                    <n:field label="Edit Ticket">
                        <s:checkbox id="ticketEditChkbx" name="accountCreateWrapper.ticketEdit" disabled="%{!accountCreateWrapper.agreementSigned}" label="Edit Ticket" theme="simple"/>
                    </n:field>
                </n:group2>
                <n:group2 id="billing-contact-info-group" titleText="Billing Contact" cssIcon="group-icon-billing-contact">
                    <n:field label="Nic Handle" fielderror="wrapper.newBillingContactId">
                        <input type="text" value="${accountCreateWrapper.billingContactNicHandle}" name="accountCreateWrapper.billingContactNicHandle" id="billingContact"/>
                        <div class="window-icon">
                            <a href="#" id="openSearchExtendedDialogbillingContact"><img src="images/skin-default/action-icons/window.png" title="Select"/></a>
                        </div>
                    </n:field>
                    <n:fielderror fields="accountCreateWrapper.billingContactNicHandle" colspan="1"/>
                    <n:field label="E-mail">
                        <input type="text" name="accountCreateWrapper.billingContactEmail" value="${accountCreateWrapper.billingContactEmail}"
                            readonly="true" id="contactEmail"
                            data-idntooltip-field="true" data-idntooltip-type="Email"/>
                    </n:field>
                </n:group2>
            </td>
            <td width="50%">
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
                    <s:submit value="Save" action="account-create" method="create" theme="simple" cssClass="xSave"/>
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
                    <s:submit value="Cancel" action="accounts-search" method="search" theme="simple" cssClass="xCancel"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>

<ticket:contacts-search-extended fieldId="billingContact" fieldEmail="contactEmail"/>

</body>
</html>