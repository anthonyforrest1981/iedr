<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Invoices</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript">
        function selectNicHandle(nicHandle, cntrl) {
            var root = $(cntrl).parents('.jqmWindow');
            var fieldId = '#' + root.children('.fieldId')[0].value;
            $(fieldId).attr('value', nicHandle);
            root.jqmHide();
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
        })
    </script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<s:form action="viewInvoices-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Settlement From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.settledFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:42%;">
                        <n:datefield2 field="searchCriteria.settledTo"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Invoice Date From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.invoiceDateFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:42%;">
                        <n:datefield2 field="searchCriteria.invoiceDateTo"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="height:26px;">
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Payment Method" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:50%;"
                              cssCtrlStyle="float:left;width:50%;">
                        <s:select list="paymentMethods" name="searchCriteria.paymentMethod"
                                  headerKey="" headerValue="[ALL]"
                                  listValue="fullName"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Customer Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:50%;"
                              cssCtrlStyle="float:left;width:50%;">
                        <s:select list="customerTypes" name="searchCriteria.customerType"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Registrar Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:50%;"
                              cssCtrlStyle="float:left;width:50%;">
                        <s:select name="searchCriteria.accountName" list="accountsByName"
                                  listKey="name" listValue="name"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Nic" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select name="searchCriteria.billingNH" list="accountsByNic"
                                  listKey="billingContact.nicHandle" listValue="billingContact.nicHandle"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                    <n:field2 label="Invoice No. from" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.invoiceNumberFrom" theme="simple" cssStyle="width:150px;text-indent:0;"/>
                    </n:field2>
                </div>

                <div style="float:left; width:50%">
                    <n:field2 label="Invoice No. to" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:42%;">
                        <s:textfield name="searchCriteria.invoiceNumberTo" theme="simple" cssStyle="width:150px;text-indent:0;"/>
                    </n:field2>
                </div>
            </div>

        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>

<s:form action="viewInvoices-viewMergedInvoices.action" theme="simple">
<n:group2 titleText="Invoices to print" cssIcon="group-icon-search">
<div class="checkbox-list-container">
    <center>
        <input type="button" value="Clear" id="do_clear" class="clear-button" />
        <s:submit id="do_print_invoices" value="Print" cssClass="action-button" />
    </center>
</div>
</n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="invoiceRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="" headerClass="checkbox-list-selectall-placeholder" media="html">
            <input
                class="print-checkbox"
                checkbox-list-value="${invoiceRow.invoiceNumber}"
                type="checkbox">
        </display:column>
        <display:column title="Invoice Number" property="invoiceNumber" sortable="true"/>
        <display:column title="Invoice Date" sortable="true" sortProperty="invoiceDate">
            <s:date name="#attr.invoiceRow.invoiceDate" format="%{getText(datePattern)}" />
        </display:column>
        <display:column title="Settlement Date" sortable="true" sortProperty="settlementDate">
            <s:date name="#attr.invoiceRow.settlementDate" format="%{getText(datePattern)}" />
        </display:column>
        <display:column title="Bill-C Nic" property="billingNicHandle" sortable="true"/>
        <display:column title="Bill-C Name" property="billingNicHandleName" sortable="true"/>
        <display:column title="Invoice Amount" sortable="true" headerClass="force-text-right" class="force-text-right" property="totalCost" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:column title="Net Amount" sortable="true" headerClass="force-text-right" class="force-text-right" property="totalNetAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:column title="Vat Amount" sortable="true" headerClass="force-text-right" class="force-text-right" property="totalVatAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:column title="Invoice Info" media="html">
            <s:div style="float: left">
                <s:url var="editUrl" action="viewInvoiceInfo-input" includeParams="none">
                    <s:param name="invoiceNumber">${invoiceRow.invoiceNumber}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="View Invoice Info" title="View Invoice Info"/>
                </s:a>
            </s:div>
        </display:column>
        <display:column title="Pdf" media="html">
            <s:div style="float: left">
                <s:url var="editUrl" action="viewPDF" includeParams="none">
                    <s:param name="invoiceNumber">${invoiceRow.invoiceNumber}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="view pdf file" title="view pdf file"/>
                </s:a>
            </s:div>
        </display:column>
        <display:setProperty name="export.csv.filename" value="invoices.csv"/>
        <display:setProperty name="export.xml.filename" value="invoices.xml"/>
    </display:table>
</n:group2>

<ticket:contacts-search fieldId="billingNH"/>

<script type="text/javascript" src="js/server-stored-list.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var addHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "viewInvoices-addToStorage.action",
            data : {selectedInvoices : values},
            complete : callback
        });
    };
    var removeHandler = function (values, callback) {
        $.ajax({
            type: "POST",
            url  : "viewInvoices-removeFromStorage.action",
            data : {selectedInvoices : values},
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
                        return "You haven't selected any invoices to print"
                    case 1:
                        return "You have selected 1 invoice"
                    default:
                        return "You have selected " + listLength + " invoices"
                }
            },
            maxSelected: 500,
            getMaxSelectedMessage: function(toSelectLength, selectedLength, limit) {
                return "Selecting " + (toSelectLength === 1 ? "this invoice" : "these invoices")
                    + " would exceed the limit (" + limit + ")";
            }
        });
});
</script>
</body>
</html>
