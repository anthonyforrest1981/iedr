<%@ attribute name="buyRequest" required="true" type="pl.nask.crs.secondarymarket.BuyRequest" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "rejectDialog";
    String openTriggerId = "openRejectDialog";
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
            .jqmAddTrigger('#<%=openTriggerId%>')
            .jqmAddClose($('.closeDialog'));
        $('#<%=dialogId%> select[@name=\'newStatus\']').change(function() {
            var warning = $('#<%=dialogId%> .warning');
            if ($(this).val() === '<s:property value="cancellingStatus"/>') {
                warning.show();
            } else {
                warning.hide();
            }
        });
    })
</script>
<div id="<%=dialogId%>" class="jqmWindow">
    <n:group2 titleText="Check In" cssIcon="group-icon-ticketcheckin">
        <center style="margin: 10px 0">
            Rejecting the buy request ${buyRequest.id} for the domain ${buyRequest.domainName}
        </center>
        <input type="hidden" name="id" value="${buyRequest.id}"/>
        <c:set var="status" value="${buyRequest.status}"/>
        <center>
            <s:select list="statuses" name="newStatus" theme="simple" listValue="description"
                         value="#attr.status" size="8"/>
        </center>
        <center class="warning" style="color: red; font-weight: bold; margin: 10px 0; display: none;">
            Rejecting buy request as <s:property value="cancellingStatus.getDescription()"/> will result in removing it from the database.
        </center>
        <hr class="buttons"/>
        <center>
            <s:submit value="Reject" method="reject" theme="simple"/>
            <input type="button" value="Cancel" class="closeDialog"/>
        </center>
    </n:group2>
</div>
