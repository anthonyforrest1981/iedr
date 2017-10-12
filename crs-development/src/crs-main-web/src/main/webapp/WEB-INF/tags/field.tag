<%@attribute name="label" required="true" %>
<%@attribute name="hideTooltipGap"
                 description="if set to true, there will be no space between label and control. False is a default"
                 type="java.lang.Boolean" %>

<%@attribute name="cssContainer" description="extra css class added to whole container" %>
<%@attribute name="cssLabel" description="extra css class added to label" %>
<%@attribute name="cssCtrl" description="extra css class added to control" %>

<%@attribute name="fielderror" rtexprvalue="true" description="name (path) of the value, fielderror should be rendered for" %>

<%@attribute name="tooltip" description="Tooltip to be displayed." %>

<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%
    if (hideTooltipGap == null) hideTooltipGap = false;

    String cssContainerClass = "ctrl-container";
    String cssLabelClass = "ctrl-label";
    String cssLabelInnerClass = "ctrl-label-inner";
    String cssCtrlClass = "ctrl-field";

    if (cssContainer != null) cssContainerClass += " " + cssContainer;
    if (cssLabel != null) cssLabelInnerClass += " " + cssLabel;
    if (cssCtrl != null) cssCtrlClass += " " + cssCtrl;

    cssContainerClass = "class=\"" + cssContainerClass + "\"";
    cssLabelClass = "class=\"" + cssLabelClass + "\"";
    cssLabelInnerClass = "class=\"" + cssLabelInnerClass + "\"";
    cssCtrlClass = "class=\"" + cssCtrlClass + "\"";

    tooltip = tooltip == null ? "" : tooltip.trim();
    boolean hasTooltip = tooltip.length() > 0;
%>

<div <%= cssContainerClass %> >

    <div <%= cssLabelClass %> >
        <div <%= cssLabelInnerClass %> >
            ${label}
        </div>

        <% if (!hideTooltipGap) { %>
        <div class="ctrl-tooltip">
        <% if (hasTooltip) { %>
            <n:tooltipIcon tooltip="${tooltip}"/>
        <% } %>
        </div>
        <% } %>
    </div>

    <div <%= cssCtrlClass %> >
        <div class="ctrl-field-inner-no-FR-gap">
            <jsp:doBody/>
        </div>
    </div>
</div>

<% if(!((fielderror==null) || ("".equals(fielderror)))) {%>
    <n:fielderror fields="${fielderror}" />
<%}%>