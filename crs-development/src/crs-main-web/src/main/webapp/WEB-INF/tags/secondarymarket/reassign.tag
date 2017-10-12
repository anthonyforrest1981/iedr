<%@ attribute name="buyRequest" required="true" type="pl.nask.crs.secondarymarket.BuyRequest" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "reassignDialog" + buyRequest.getId();
    String openTriggerId = "openReassignDialog" + buyRequest.getId();
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })
</script>
<div id="<%=dialogId%>" class="jqmWindow">
    <form action="buy-requests-browser-reassign.action" method="post">
        <n:group2 titleText="Reassign" cssIcon="group-icon-ticketreassign">
            <center style="margin: 10px 0">
                Reassigning the buy request ${buyRequest.id} for the domain ${buyRequest.domainName}
            </center>
            <input type="hidden" name="id" value="${buyRequest.id}"/>
            <c:set var="checkedOutTo" value="${buyRequest.checkedOutTo}"/>
            <center>
                <s:select list="hostmasters" name="newHostmaster" theme="simple" listKey="username" listValue="name"
                          value="#attr.checkedOutTo"/>
            </center>
            <hr class="buttons"/>
            <center>
                <input type="Submit" value="Submit"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>
        </n:group2>
    </form>
</div>
