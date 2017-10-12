<%@tag import="pl.nask.crs.web.ticket.wrappers.NameserverWrapper" %>
<%@tag import="pl.nask.crs.ticket.operation.IpFailureReason" %>

<%@ attribute name="ns" required="false" type="pl.nask.crs.web.ticket.wrappers.NameserverWrapper" %>
<%@ attribute name="order" required="false" type="java.lang.String" %>
<%@ attribute name="inputDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="frDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="nameFailureReasons" required="true" type="java.util.List" %>
<%@ attribute name="ipFailureReasons" required="true" type="java.util.List" %>
<%@ attribute name="nsNameClass" required="false" type="java.lang.String" %>
<%@ attribute name="nsIpv4Class" required="false" type="java.lang.String" %>
<%@ attribute name="nsIpv6Class" required="false" type="java.lang.String" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    // Filling empty values in case of this being a template for a new nameserver.
    NameserverWrapper w = (NameserverWrapper) jspContext.getAttribute("ns");
    if (w == null) {
        w = new NameserverWrapper();
        jspContext.setAttribute("ns", w);
    }
    String order = (String) jspContext.getAttribute("order");
    if (order == null) {
        jspContext.setAttribute("order", "%ID%");
    }
%>

<li class="ticket-nameserver">
    <div class="ctrl-container">
        <div class="field-label">Name</div>
        <div class="ctrl-field">
            <input class="${nsNameClass}" type="text" value="${ns.name}"
                name="ticketWrapper.newNameserverWrappers[${order}].name" ${inputDisabled ? "disabled" : ""}
                data-idntooltip-field="true" data-idntooltip-type="Nameserver"/>
        </div>
        <% if (!frDisabled) {%>
        <div class="failure-reason">
            <s:select list="#attr.nameFailureReasons" headerKey="null" headerValue="(none)" listValue="description"
                         theme="simple" disabled="#attr.frDisabled">
                <s:param name="name">ticketWrapper.newNameserverWrappers[${order}].nameFr</s:param>
                <s:param name="value">${ns.nameFr}</s:param>
            </s:select>
        </div>
        <% } else { %>
        <div class="failure-reason read-only">
            <%= w.getNameFr() == null ? "" : w.getNameFr().getDescription() %>
        </div>
        <% } %>
    </div>

    <% String nnames = "ticketWrapper.nameserverWrappers[" + order + "].name,ticketWrapper.nameserverWrappers[" + order + "].nameFr"; %>
    <n:fielderror fields="<%=nnames %>"/>

    <div class="ctrl-container">
        <div class="field-label">IPv4</div>
        <div class="ctrl-field">
            <input class="${nsIpv4Class}" type="text" value="${ns.ipv4}"
                     name="ticketWrapper.newNameserverWrappers[${order}].ipv4" ${inputDisabled ? "disabled" : ""}/>
        </div>
        <% if (!frDisabled) {%>
        <div class="failure-reason">
            <s:select list="#attr.ipFailureReasons" headerKey="null" headerValue="(none)" listValue="description"
                         theme="simple" disabled="#attr.frDisabled">
                <s:param name="name">ticketWrapper.newNameserverWrappers[${order}].ipv4Fr</s:param>
                <s:param name="value">${ns.ipv4Fr}</s:param>
            </s:select>
        </div>
        <% } else { %>
        <div class="failure-reason read-only">
            <%= w.getIpv4Fr() == null ? "" : w.getIpv4Fr().getDescription() %>
        </div>
        <% } %>
    </div>

    <% String ipv4names = "ticketWrapper.nameserverWrappers[" + order + "].ipv4,ticketWrapper.nameserverWrappers[" + order + "].ipv4Fr"; %>
    <n:fielderror fields="<%=ipv4names %>"/>

    <div class="ctrl-container">
        <div class="field-label">IPv6</div>
        <div class="ctrl-field">
            <input class="${nsIpv6Class}" type="text" value="${ns.ipv6}"
                     name="ticketWrapper.newNameserverWrappers[${order}].ipv6" ${inputDisabled ? "disabled" : ""}/>
        </div>
        <% if (!frDisabled) {%>
        <div class="failure-reason">
            <s:select list="#attr.ipFailureReasons" headerKey="null" headerValue="(none)" listValue="description"
                         theme="simple" disabled="#attr.frDisabled">
                <s:param name="name">ticketWrapper.newNameserverWrappers[${order}].ipv6Fr</s:param>
                <s:param name="value">${ns.ipv6Fr}</s:param>
            </s:select>
        </div>
        <% } else { %>
        <div class="failure-reason read-only">
            <%= w.getIpv6Fr() == null ? "" : w.getIpv6Fr().getDescription() %>
        </div>
        <% } %>
    </div>

    <% String ipv6names = "ticketWrapper.nameserverWrappers[" + order + "].ipv6,ticketWrapper.nameserverWrappers[" + order + "].ipv6Fr"; %>
    <n:fielderror fields="<%=ipv6names %>"/>

    <% if (!inputDisabled) {%>
        <a class="button">Remove</a>
    <% } %>
</li>
