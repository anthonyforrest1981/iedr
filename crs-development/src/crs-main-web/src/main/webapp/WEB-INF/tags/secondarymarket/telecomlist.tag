<%@ attribute name="wrapper" required="true" type="java.lang.String" rtexprvalue="false"
                  description="path to the phone wrapper" %>
<%@ attribute name="inputDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="label" required="true" description="label to be used" %>
<%@attribute name="reasonField" required="true" rtexprvalue="false"
             description="failure reason field name - must be an instance of SimpleDomainFieldChangeWrapper" %>
<%@attribute name="reasonEditable" type="java.lang.Boolean" required="true"
             description="Informs tag that its fail reason is a pure text (no select or input). +Default is False." %>
<%@attribute name="reasonHidden" type="java.lang.Boolean" required="true"
             description="if set to true, there will be no fail reason displayed. Default is False." %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@ tag import="pl.nask.crs.app.nichandles.wrappers.PhoneWrapper" %>
<%@ tag import="pl.nask.crs.ticket.operation.FailureReason" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.Arrays" %>

<%
    PhoneWrapper o = (PhoneWrapper) jspContext.getVariableResolver().resolveVariable(wrapper);
    jspContext.setAttribute("list", o.getCurrentList());
    jspContext.setAttribute("field", wrapper + ".phone");

    String reasonName = reasonField + ".description";
    List reasonsList = Arrays.asList(FailureReason.values());
    FailureReason failureReason = (FailureReason) jspContext.getVariableResolver().resolveVariable(reasonField);
    String boxReasonClass = "FCB-reason";
    String reasonValueClass = "FCR-value";
    jspContext.setAttribute("reasonsList", reasonsList);
    jspContext.setAttribute("frFieldName", reasonName);
    jspContext.setAttribute("frIdFieldName", reasonField);
    jspContext.setAttribute("frIdValue", failureReason);
%>

<c:if test="${!inputDisabled}">
    <script type="text/javascript" language="javascript">
        function addRow_${label}() {
            var ni = document.getElementById('nsTable_${label}');
            var numi = document.getElementById('sequence_${label}');
            var num = (document.getElementById("sequence_${label}").value - 1) + 2;
            numi.value = num;
            var divIdName = "my_${label}" + num + "Div";
            var newdiv = document.createElement('div');
            newdiv.setAttribute("id", divIdName)

            var input_field = "<input type=\"text\" name=\"${field}\">"

            //fields
            var div_close = "</div>";

            var ctrl_container = "<div class=\"ctrl-container\">";
            var ctrl_label = "<div class=\"ctrl-label\">";
            var ctrl_tooltip = "<div class=\"ctrl-tooltip\">";
            var ctrl_field = "<div class=\"ctrl-field force-width-no-max\">";
            var ctrl_inner = "<div class=\"ctrl-field-inner-no-FR-gap\">";
            var ctrl_field_icon = "<div class=\"ctrl-field-icon\">" +
                                         "<a href=\"javascript:;\" onclick=\"removeElement_${label}(\'" + divIdName + "\')\">" +
                                         "<img src=\"images/skin-default/action-icons/delete.png\" alt=\"X\"/>" +
                                         "</a>";

            var my_inner = ctrl_container;
            my_inner += ctrl_label + "${label}" + div_close;
            my_inner += ctrl_tooltip + div_close;
            my_inner += ctrl_field;
            my_inner += ctrl_inner;
            my_inner += input_field;
            my_inner += ctrl_field_icon + div_close;
            my_inner += div_close;
            my_inner += div_close;
            my_inner += div_close;

            newdiv.innerHTML = my_inner;

            //Name field
            ni.appendChild(newdiv);
        }

        function removeElement_${label}(divNum) {
            var d = document.getElementById('nsTable_${label}');
            var olddiv = document.getElementById(divNum);
            d.removeChild(olddiv);
        }
    </script>

    <input type="hidden" value="0" id="sequence_${label}"/>
</c:if>

<div class="phonefax_header">
    ${label}
    <div class="<%=boxReasonClass%>" style="position: relative; top: -3px;">
        <% if (!reasonHidden) { %>
        <div class="<%=reasonValueClass%>">
            <% if (reasonEditable) { %>
                <s:select list="#attr.reasonsList" headerKey="null" headerValue="(none)"
                          listValue="description" theme="simple">
                    <s:param name="name">${frIdFieldName}</s:param>
                    <s:param name="value">${frIdValue}</s:param>
                </s:select>
            <% } else { %>
                <%= failureReason != null ? failureReason.getDescription() : "" %>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
<n:fielderror colspan="4" fields="<%=reasonField%>"/>
<div id="telecomEmpty" style="display: none;">
    <%-- defend against empty lists --%>
    <input type="" value="" name="${field}" ${inputDisabled ? "disabled" : ""}/>
</div>
<div id="nsTable_${label}">
    <c:forEach items="${list}" var="ns" varStatus="stat">
        <c:set var="num" value="${stat.count-1}"/>
        <%
            Long num = (Long) jspContext.getAttribute("num");
            jspContext.setAttribute("fieldId", "cns_" + label + num + "div");
        %>
        <div id="${fieldId}">
            <n:field label="${label}" cssCtrl="force-width-no-max">
                <input class="${nsNameClass} force-width-very-long" type="text" value="${ns}"
                         name="${field}" ${inputDisabled ? "disabled" : ""}/>

                <c:if test="${!inputDisabled}">
                    <div class="ctrl-field-icon">
                        <a href="javascript:;" onclick="removeElement_${label}('${fieldId}')">
                            <img src="images/skin-default/action-icons/delete.png" alt="X"/>
                        </a>
                    </div>
                </c:if>

            </n:field>
            <n:fielderror fields="${wrapper}[${num}]"/>
        </div>
    </c:forEach>
</div>
<n:fielderror fields="${wrapper}"/>
<c:if test="${!inputDisabled }">
    <hr class="buttons"/>
    <center>
        <input type="button" name="add" value="Add entry" onclick="addRow_${label}()"/>
    </center>
</c:if>
