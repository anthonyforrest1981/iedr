<%@ attribute name="nameservers" required="true" type="java.util.List" %>
<%@ attribute name="modifyingTicket" required="true" type="java.lang.Boolean" %>
<%@ attribute name="inputDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="frDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="nameFailureReasons" required="true" type="java.util.List" %>
<%@ attribute name="ipFailureReasons" required="true" type="java.util.List" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>

<%@tag import="pl.nask.crs.web.ticket.wrappers.NameserverWrapper" %>

<style>
#ticket-nameservers {
    padding: 0;
    list-style: none;
}
.ticket-nameserver {
    margin-bottom: 10px;
}
#add-nameserver {
    margin-top: 20px;
    margin-bottom: 20px;
}
#add-nameserver,
.ticket-nameserver .button {
    border: 1px solid #ABAEAB;
    display: block;
    margin-left: 70px;
    padding: 5px;
    text-align: center;
    width: 65px;
}
#add-nameserver:hover,
.ticket-nameserver .button:hover {
    border: 1px solid black;
    background: none;
}
.ticket-nameserver .field-label {
    float:left;
    width:15%;
    line-height: 1.2em;
}
.ticket-nameserver .ctrl-field       { width: 65%; }
.ticket-nameserver .ctrl-field input { width: 200px; }
.ticket-nameserver .failure-reason {
    float:right;
    width:20%;
}
.ticket-nameserver .failure-reason.read-only {
    padding-top: 6px;
}
.ticket-nameserver .failure-reason select {
    width:100%;
}

</style>

<ol id="ticket-nameservers">

<c:forEach items="${nameservers}" var="ns" varStatus="stat">
    <c:set var="num" value="${stat.count-1}"/>

    <%
    Long num = (Long) jspContext.getAttribute("num");
    NameserverWrapper w = (NameserverWrapper) jspContext.getAttribute("ns");
    if (modifyingTicket && w.isNameModification())
        jspContext.setAttribute("nsNameClass", "modification");
    else
        jspContext.setAttribute("nsNameClass", "");

    if (modifyingTicket && w.isIpv4Modification())
        jspContext.setAttribute("nsIpv4Class", "modification");
    else
        jspContext.setAttribute("nsIpv4Class", "");

    if (modifyingTicket && w.isIpv6Modification())
        jspContext.setAttribute("nsIpv6Class", "modification");
    else
        jspContext.setAttribute("nsIpv6Class", "");
    %>
    <ticket:nameserver ns="${ns}" order="${num}"
        inputDisabled="${inputDisabled}" frDisabled="${frDisabled}"
        nameFailureReasons="${nameFailureReasons}" ipFailureReasons="${ipFailureReasons}"
        nsNameClass="${nsNameClass}" nsIpv4Class="${nsIpv4Class}" nsIpv6Class="${nsIpv6Class}" />
</c:forEach>
</ol>

<n:fielderror fields="ticketWrapper.nameservers"/>

<script type="text/javascript">
    $(document).ready(function() {
        var ticketNameservers = $("#ticket-nameservers");
        var setUpIdnTooltips = function(nameserverInput) {
            nameserverInput.idnTooltip({
                iconContainer: function(element) {
                    return $(".field-label", element.parents(".ctrl-container"));
                },
                type: nameserverInput.attr("data-idntooltip-type")
            });
        };
        ticketNameservers.data("setUpIdnTooltips", setUpIdnTooltips);
        $("[data-idntooltip-field='true']", ticketNameservers).each(function() {
            setUpIdnTooltips($(this));
        });
    });
</script>

<% if (!inputDisabled) {%>

<a id="add-nameserver" class="button">Add</a>

<script id="ticket-nameserver-template" type="text/template">
    <ticket:nameserver
        inputDisabled="false" frDisabled="false"
        nameFailureReasons="${nameFailureReasons}" ipFailureReasons="${ipFailureReasons}" />
</script>

<script type="text/javascript">
$(function() {
    var ticketNameservers = $("#ticket-nameservers"),
        setUpIdnTooltips = ticketNameservers.data("setUpIdnTooltips"),
        next_ns_idx = $(".ticket-nameserver").size(),
        nameserverTpl = $("#ticket-nameserver-template").text();
    var removeCallback = function() {
        var liElem = $(this).parents(".ticket-nameserver"),
            mappings = {
                '%NAME%' : $("input[name~=name]", liElem).val() || 'blank',
                '%IPV4%'   : $("input[name~=ipv4]", liElem).val() || 'blank',
                '%IPV6%'   : $("input[name~=ipv6]", liElem).val() || 'blank'} ,
            msg = "Name: %NAME%\nIPv4: %IPV4%\nIPv6: %IPV6%\nAre you sure you want to delete this entry?".replace(/%[\w]+%/g, function(match) {
                return mappings[match] || match;
            });
        if (confirm(msg))
            $(this).parents(".ticket-nameserver").remove();
        return false;
    };
    $(".ticket-nameserver .button").bind("click", removeCallback);
    $("#add-nameserver").bind("click", function() {
        var newElem = $(nameserverTpl.replace(/%ID%/g, next_ns_idx++));
        $(".button", newElem).bind("click", removeCallback);
        $("#ticket-nameservers").append(newElem);
        setUpIdnTooltips($("[data-idntooltip-field='true']", newElem));
        return false;
    });
});
</script>
<% } %>
