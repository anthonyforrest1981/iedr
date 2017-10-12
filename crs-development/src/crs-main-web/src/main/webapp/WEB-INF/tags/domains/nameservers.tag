<%@ attribute name="wrapper" required="true" type="java.lang.String" rtexprvalue="false"
              description="path to the dns wrapper" %>
<%@ attribute name="inputDisabled" required="true" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@tag import="pl.nask.crs.app.domains.wrappers.DnsWrapper" %>

<%
    jspContext.setAttribute("nsNameClass", "");
    jspContext.setAttribute("nsIpClass", "");

    DnsWrapper o = (DnsWrapper) jspContext.getVariableResolver().resolveVariable(wrapper);
    jspContext.setAttribute("nslist", o.getNameservers());
    String nameField = wrapper + ".newNames";
    String ipv4Field = wrapper + ".newIpv4s";
    String ipv6Field = wrapper + ".newIpv6s";
    jspContext.setAttribute("nameField", nameField);
    jspContext.setAttribute("ipv4Field", ipv4Field);
    jspContext.setAttribute("ipv6Field", ipv6Field);
%>

<script type="text/javascript">
    (function(){
        var sequence = 0;
        var setUpIdnTooltips = function(nameserverInput) {
            nameserverInput.idnTooltip({
                iconContainer: function(element) {
                    return $(".ctrl-tooltip", element.parents(".ctrl-container"));
                },
                type: nameserverInput.attr("data-idntooltip-type")
            });
        };
        var removeRow = function(row) {
            var name = $("[name='${nameField}']", row).val();
            var ipv4 = $("[name='${ipv4Field}']", row).val();
            var ipv6 = $("[name='${ipv6Field}']", row).val();
            if (confirm('Name: '+name+'\nIPv4: '+ipv4+'\nIPv6: '+ipv6+'\nAre you sure you want to delete this entry? ')) {
                row.remove();
            }
        };
        var addRow = function(container) {
            sequence += 1;
            var divIdName = "my" + sequence + "Div";
            var newdiv = document.createElement('div');
            newdiv.setAttribute("id", divIdName);

            var wrapper = '<%= wrapper%>';
            var ognl_ipv4 = wrapper + ".newIpv4s";
            var ognl_ipv6 = wrapper + ".newIpv6s";
            var ognl_ns = wrapper + ".newNames";

            var input_ns = "<input type=\"text\" name=\"" + ognl_ns + "\" " +
                "data-idntooltip-field=\"true\" data-idntooltip-type=\"Nameserver\">";
            var input_ipv4 = "<input type=\"text\" name=\"" + ognl_ipv4 + "\">";
            var input_ipv6 = "<input type=\"text\" name=\"" + ognl_ipv6 + "\">";

            //fields
            var div_close = "</div>";

            var ctrl_container = "<div class=\"ctrl-container\">";
            var ctrl_label = "<div class=\"ctrl-label\">";
            var ctrl_label_inner = "<div class=\"ctrl-label-inner\">";
            var ctrl_tooltip = "<div class=\"ctrl-tooltip\">";
            var ctrl_field = "<div class=\"ctrl-field force-width-no-max\">";
            var ctrl_inner = "<div class=\"ctrl-field-inner-no-FR-gap\">";
            var ctrl_field_icon = "<div class=\"ctrl-field-icon\">" +
                                        "<a href=\"javascript:;\">" +
                                        "<img src=\"images/skin-default/action-icons/delete.png\" alt=\"X\"/>" +
                                        "</a>";

            var my_inner = ctrl_container;
            my_inner += ctrl_label + ctrl_label_inner + "Name" + div_close;
            my_inner += ctrl_tooltip + div_close + div_close;
            my_inner += ctrl_field;
            my_inner += ctrl_inner;
            my_inner += input_ns;
            my_inner += ctrl_field_icon + div_close;
            my_inner += div_close;
            my_inner += div_close;
            my_inner += div_close;

            my_inner += ctrl_container;
            my_inner += ctrl_label + "IPv4" + div_close;
            my_inner += ctrl_tooltip + div_close;
            my_inner += ctrl_field;
            my_inner += ctrl_inner;
            my_inner += input_ipv4;
            my_inner += div_close;
            my_inner += div_close;
            my_inner += div_close;

            my_inner += ctrl_container;
            my_inner += ctrl_label + "IPv6" + div_close;
            my_inner += ctrl_tooltip + div_close;
            my_inner += ctrl_field;
            my_inner += ctrl_inner;
            my_inner += input_ipv6;
            my_inner += div_close;
            my_inner += div_close;
            my_inner += div_close;
            my_inner += "<br/>";

            newdiv.innerHTML = my_inner;
            var newRow = $(newdiv).appendTo(container);
            $(".ctrl-field-icon > a", newRow).click(removeRow.bind(this, newRow));
            setUpIdnTooltips($("[name='${nameField}']", newRow));
        };
        $(document).ready(function() {
            $("#domain-nameservers [data-idntooltip-field='true']").each(function() {
                setUpIdnTooltips($(this));
            });
            $("#domain-nameservers [name='add']").click(addRow.bind(this, $("#nsTable")));
            $("#domain-nameservers .domain-nameserver").each(function() {
                $(".ctrl-field-icon > a", $(this)).click(removeRow.bind(this, $(this)));
            });
        });
    }());
</script>

<div id="domain-nameservers">
    <div id="nsEmptyNS" style="display: none;">
        <%-- defend against empty lists --%>
        <input value="" name="${ipv4Field}" ${inputDisabled ? "disabled" : ""}/>
        <input value="" name="${ipv6Field}" ${inputDisabled ? "disabled" : ""}/>
        <input value="" name="${nameField}" ${inputDisabled ? "disabled" : ""}/>
    </div>

    <div id="nsTable">
        <c:forEach items="${nslist}" var="ns" varStatus="stat">
            <c:set var="num" value="${stat.count-1}"/>
            <%
                Long num = (Long) jspContext.getAttribute("num");
                String fieldId = "cns" + num + "div";
                String nameFieldFe = wrapper + ".nameservers["+num+"].name";
                String ipv4FieldFe = wrapper + ".nameservers["+num+"].ipv4";
                String ipv6FieldFe = wrapper + ".nameservers["+num+"].ipv6";

                jspContext.setAttribute("fieldId", fieldId);
                jspContext.setAttribute("ipv4FieldFe", ipv4FieldFe);
                jspContext.setAttribute("ipv6FieldFe", ipv6FieldFe);
                jspContext.setAttribute("nameFieldFe", nameFieldFe);
            %>
            <div id="${fieldId}" class="domain-nameserver">
                <n:field label="Name" cssCtrl="force-width-no-max">
                    <input id="${fieldId}_name" class="${nsNameClass} force-width-very-long" type="text" value="${ns.name}"
                            name="${nameField}" ${inputDisabled ? "disabled" : ""}
                            data-idntooltip-field="true" data-idntooltip-type="Nameserver"/>

                    <c:if test="${!inputDisabled}">
                        <div class="ctrl-field-icon">
                            <a href="javascript:;">
                                <img src="images/skin-default/action-icons/delete.png" alt="X"/>
                            </a>
                        </div>
                    </c:if>
                </n:field>
                <n:fielderror fields="${nameFieldFe}"/>
                <n:field label="IPv4" cssCtrl="force-width-no-max">
                    <input id="${fieldId}_ipv4" class="${nsIpClass} force-width-very-long" type="text" value="${ns.ipv4Address}"
                            name="${ipv4Field}" ${inputDisabled ? "disabled" : ""}/>
                </n:field>
                <n:fielderror fields="${ipv4FieldFe}"/>

                <n:field label="IPv6" cssCtrl="force-width-no-max">
                    <input id="${fieldId}_ipv6" class="${nsIpClass} force-width-very-long" type="text" value="${ns.ipv6Address}"
                            name="${ipv6Field}" ${inputDisabled ? "disabled" : ""}/>
                </n:field>
                <n:fielderror fields="${ipv6FieldFe}"/>
                <br/>
            </div>
        </c:forEach>
    </div>
    <n:fielderror fields="${wrapper}" />
    <c:if test="${!inputDisabled}">
        <hr class="buttons"/>
        <center>
            <input type="button" name="add" value="Add entry"/>
        </center>
    </c:if>
</div>
