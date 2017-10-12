<%@ attribute name="buyRequest" required="true" type="pl.nask.crs.secondarymarket.BuyRequest" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "checkInDialog" + buyRequest.getId();
    String openTriggerId = "openCheckInDialog" + buyRequest.getId();
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                  .jqmAddTrigger('#<%=openTriggerId%>')
                  .jqmAddClose($('.closeDialog'));
    })
</script>
<div id="<%=dialogId%>" class="jqmWindow">
    <form action="buy-requests-browser-checkin.action" method="post">
        <n:group2 titleText="Check In" cssIcon="group-icon-ticketcheckin">
            <center style="margin: 10px 0">
                Checking in the buy request ${buyRequest.id} for the domain ${buyRequest.domainName}
            </center>
            <input type="hidden" name="id" value="${buyRequest.id}"/>
            <c:set var="status" value="${buyRequest.status}"/>
            <center>
                <s:select list="statuses" name="newStatus" theme="simple" listValue="description"
                             value="#attr.status" size="8"/>
            </center>
            <hr class="buttons"/>
            <center>
                <input type="Submit" value="Submit"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>
        </n:group2>
    </form>
</div>
