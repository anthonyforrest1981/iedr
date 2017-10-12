<%@attribute name="label" required="true" %>

<%@attribute name="tooltip" description="Tooltip to be displayed." %>
<%@attribute name="tooltipGapHidden" type="java.lang.Boolean"
                 description="if set to true, there will be no space between label and control.
                 Default is False." %>

<%@attribute name="field" required="true" rtexprvalue="false"
                 description="input field name - must be an instance of SimpleDomainFieldChangeWrapper" %>
<%@attribute name="fieldEditable" type="java.lang.Boolean"
                 description="Informs tag that its jsp:doBody has no select or input.
                 Default is False." %>

<%@attribute name="selectorGapHidden" type="java.lang.Boolean"
                 description="if set to true, there will be no extra-space between control and fail reason.
                 Default is False." %>

<%@attribute name="reasonField" required="false" rtexprvalue="false"
             description="failure reason field name - must be an instance of FailureReason" %>
<%@attribute name="reasonEditable" type="java.lang.Boolean"
                 description="Informs tag that its fail reason is a pure text (no select or input).
                 Default is False." %>
<%@attribute name="reasonHidden" type="java.lang.Boolean"
                 description="if set to true, there will be no fail reason displayed.
                 Default is False." %>
<%@attribute name="reasonGapHidden" type="java.lang.Boolean"
                 description="if set to true, there will be no extra-space between fail reason and right end of container.
                 Default is False." %>

<%@attribute name="multiline" type="java.lang.Boolean" required="false" %>

<%@attribute name="cssContainerStyle" description="extra css style added to whole container" %>
<%@attribute name="cssLabelStyle" description="extra css style added to label" %>
<%@attribute name="cssCtrlStyle" description="extra css style added to control" %>
<%@attribute name="cssReasonStyle" description="extra css style added to control" %>

<%@attribute name="idnTooltip" type="java.lang.Boolean" required="false" %>
<%@attribute name="idnTooltipType" type="java.lang.String" required="false" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@tag import="java.util.List" %>
<%@tag import="pl.nask.crs.ticket.operation.FailureReason" %>
<%@ tag import="java.util.Arrays" %>

<%
    // classes
    String containerClass; // whole field

    String labelClass; // label with icon
    String labelBoxClass; // label without icon
    String labelValueClass; // inner label element

    String boxClass; // ctrl with reason
    String boxBoxClass; // ctrl without reason
    String boxReasonClass; // failure reason

    String reasonValueClass; // inner reason element

    String ctrlBoxClass; // ctrl without icon
    String ctrlValueClass; // inner ctrl element

    containerClass = "FC";

    labelClass = "FCL";
    labelBoxClass = "FCL-box";
    labelValueClass = "FCL-value textOnly justify-left";

    boxClass = "FCB";
    boxBoxClass = "FCB-box";
    boxReasonClass = "FCB-reason";

    reasonValueClass = "FCR-value";

    ctrlBoxClass = "FCC-box";
    ctrlValueClass = "FCC-value";

    // extra style
    String containerStyle = ""; // whole field
    String labelStyle = "";
    String boxStyle = "";
    String ctrlStyle = ""; // control which displays value select/input/text
    String reasonStyle = ""; // control which displays fail reason (select)

    if (cssContainerStyle != null) containerStyle += " " + cssContainerStyle;
    if (cssLabelStyle != null)
        labelStyle += " " + cssLabelStyle;
    else
        labelClass += " default-label-width";
    if (cssCtrlStyle != null) boxStyle += " " + cssCtrlStyle;
    if (cssReasonStyle != null) reasonStyle += " " + cssReasonStyle;

    String boxBoxStyle = "";
    String ctrlBoxStyle = "";
    String ctrlValueStyle = "";
    if (multiline == null) multiline = false;
    if (multiline) {
        containerStyle += "height: auto;";
        boxStyle += "height: auto";
        boxBoxStyle += "height: auto";
        ctrlBoxStyle += "height: auto;";
        ctrlValueStyle += "white-space: pre-wrap;";
    }

    if (fieldEditable == null) fieldEditable = false;
    if (!fieldEditable) ctrlValueClass += " textOnly";

    Object fieldValue = jspContext.getVariableResolver().resolveVariable(field);

    if (fieldValue == null) fieldValue = "";

    // tooltip
    if (tooltipGapHidden == null) tooltipGapHidden = false;
    if (!tooltipGapHidden) labelBoxClass += " tooltip-gap";

    tooltip = tooltip == null ? "" : tooltip.trim();
    boolean hasTooltip = tooltip.length() > 0;

    // box
    if (cssLabelStyle == null)
        boxClass += " default-label-gap";

    // field selector
    boolean selectorHidden = false;

    if (selectorGapHidden == null) selectorGapHidden = false;
    if (selectorGapHidden == true) selectorHidden = true;

    if (!selectorGapHidden) ctrlBoxClass += " selector-gap";

    // reason
    if (reasonHidden == null) reasonHidden = false;
    if (reasonEditable == null) reasonEditable = false;
    if (reasonGapHidden == null) reasonGapHidden = false;

    if (reasonGapHidden == true) {
        reasonHidden = true;
        reasonEditable = false;
    } else {
        boxBoxClass += " reason-gap";
    }

    if (reasonEditable == false) reasonValueClass += " textOnly";

    String reasonName = reasonField + ".description";
    List reasonsList = Arrays.asList(FailureReason.values());

    FailureReason n = (FailureReason) jspContext.getVariableResolver().resolveVariable(reasonField);

    String idnTooltipFieldsAttributes = "";
    if (idnTooltip == null) idnTooltip = false;
    if (idnTooltipType == null) idnTooltipType = "Value";
    if (idnTooltip) {
        idnTooltipFieldsAttributes += " data-idntooltip-field=\"true\"" +
            " data-idntooltip-type=\"" + idnTooltipType + "\"";
    }

    jspContext.setAttribute("reasonsList", reasonsList);
    jspContext.setAttribute("frFieldName", reasonName);
    jspContext.setAttribute("frIdFieldName", reasonField);
    jspContext.setAttribute("frIdValue", n);
%>

<div class="<%=containerClass%>" style="<%=containerStyle%>">

    <!-- label -->
    <div class="<%=labelClass%>" style="<%=labelStyle%>">

        <!-- label (tooltip) icon -->
        <% if (!tooltipGapHidden) { %>
        <div class="FCL-icon">
            <% if (hasTooltip) { %>
            <n:tooltipIcon tooltip="${tooltip}"/>
            <% } %>
        </div>
        <% } %>

        <%-- label text --%>
        <div class="<%=labelBoxClass%>">
            <div class="<%=labelValueClass%>">
                ${label}
            </div>
        </div>
    </div>

    <!-- ctrl + reason -->
    <div class="<%=boxClass%>" style="<%=boxStyle%>">

        <!-- reason -->
        <% if (!reasonGapHidden) { %>
        <div class="<%=boxReasonClass%>">
            <% if (!reasonHidden) { %>
            <div class="<%=reasonValueClass%>" style="<%=reasonStyle%>">
                <% if (reasonEditable) { %>
                <s:select list="#attr.reasonsList" headerKey="null" headerValue="(none)"
                             listValue="description" theme="simple">
                    <s:param name="name">${frIdFieldName}</s:param>
                    <s:param name="value">${frIdValue}</s:param>
                </s:select>
                <% } else { %>
                    <% String s = (String) jspContext.getVariableResolver().resolveVariable(reasonName); %>
                    <%= s != null ? s : "" %>
                <% } %>
            </div>
            <% } %>
        </div>
        <% } %>

        <!-- ctrl without reason -->
        <div class="<%=boxBoxClass%>" style="<%=boxBoxStyle%>">

            <!-- ctrl value -->
            <div class="<%=ctrlBoxClass%>" style="<%=ctrlBoxStyle%>">
                <% if (fieldEditable) { %>
                    <div class="<%=ctrlValueClass%>">
                        <% if (multiline) { %>
                            <textarea name="<%= field %>"><%= fieldValue%></textarea>
                        <% } else { %>
                            <input type="text" value="<%= fieldValue%>" name="<%= field %>"<%= idnTooltipFieldsAttributes %>/>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="<%=ctrlValueClass%>" style="<%=ctrlValueStyle%>"<%= idnTooltipFieldsAttributes %>><%= fieldValue%></div>
                <% } %>
            </div>

            <!-- ctrl icon -->
            <% if (!selectorGapHidden) { %>
            <div class="FCC-icon">
                <jsp:doBody/>
            </div>
            <% } %>
            <% if (multiline) { %>
                <div style="clear: both"></div>
            <% } %>
        </div>
    </div>
</div>

<% String fnl = field + "," + reasonField ; %>
<n:fielderror colspan="4" fields="<%= fnl%>"/>
