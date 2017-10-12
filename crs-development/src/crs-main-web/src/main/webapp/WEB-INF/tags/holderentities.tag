<%@ attribute name="id" required= "true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="classList" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="categoryList" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="classField" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="classCssClass" required="false" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute name="categoryField" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="categoryCssClass" required="false" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute name="subcategoryField" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="subcategoryCssClass" required="false" type="java.lang.String" rtexprvalue="true" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ tag import="java.util.List" %>
<%@ tag import="pl.nask.crs.entities.EntityClass" %>
<%@ tag import="pl.nask.crs.entities.EntityCategory" %>

<%
    List<EntityClass> classes = (List<EntityClass>) jspContext.getVariableResolver().resolveVariable(classList);
    List<EntityCategory> categories =
        (List<EntityCategory>) jspContext.getVariableResolver().resolveVariable(categoryList);
    Long selectedClassId = (Long) jspContext.getVariableResolver().resolveVariable(classField);
    Long selectedCategoryId = (Long) jspContext.getVariableResolver().resolveVariable(categoryField);
    Long selectedSubcategoryId = (Long) jspContext.getVariableResolver().resolveVariable(subcategoryField);
    jspContext.setAttribute("classes", classes);
    jspContext.setAttribute("categories", categories);
    jspContext.setAttribute("selectedClassId", selectedClassId);
    jspContext.setAttribute("selectedCategoryId", selectedCategoryId);
    jspContext.setAttribute("selectedSubcategoryId", selectedSubcategoryId);
%>

<s:set var="selectedClassId"><%=selectedClassId%></s:set>
<s:set var="selectedCategoryId"><%=selectedCategoryId%></s:set>
<s:set var="selectedSubcategoryId"><%=selectedSubcategoryId%></s:set>
<s:set var="classCssClass"><%=classCssClass%></s:set>
<s:set var="categoryCssClass"><%=categoryCssClass%></s:set>
<s:set var="subcategoryCssClass"><%=subcategoryCssClass%></s:set>

<script type="text/javascript">
    $(document).ready(function() {
        var setSameValueIfExists = function(select, commonClass) {
            var value = $("." + commonClass + " > select:enabled").val();
            var option = $("option[value='" + value + "']", select);
            if (option.length === 0) {
                value = $("option:first", select).val();
            }
            select.val(value);
        };

        var hideAllButOne = function(commonClass, visibleId) {
            $("." + commonClass).hide()
            $("." + commonClass + " > select").attr("disabled", "disabled");
            var container = $("#" + visibleId);
            var select = $("select", container);
            select.removeAttr("disabled");
            container.show()
        };

        $(".select-class > select").change(function() {
            var classId = $(this).val();
            var categoryContainerId = "categorySelect_${id}_" + classId;
            var categorySelect = $("#" + categoryContainerId + " > select");
            setSameValueIfExists(categorySelect, "select-category");
            hideAllButOne("select-category", categoryContainerId);

            categorySelect.change();
        });

        var emptySubcategory = $("#emptySubcategory");
        $(".select-category > select").change(function() {
            var categoryId = $(this).val();
            var subcategoryContainerId = "subcategorySelect_${id}_" + categoryId;
            var subcategorySelect = $("#" + subcategoryContainerId + " > select");
            setSameValueIfExists(subcategorySelect, "select-subcategory");
            hideAllButOne("select-subcategory", subcategoryContainerId);

            if (subcategorySelect.length > 0) {
                emptySubcategory.attr("disabled", "disabled");
            } else {
                emptySubcategory.removeAttr("disabled");
            }
        });
    });
</script>

<div class="select-class">
    <s:select list="#attr.classes" listKey="id" listValue="name" cssClass="%{#classCssClass}">
        <s:param name="value">${selectedClassId}</s:param>
        <s:param name="name">${classField}</s:param>
    </s:select>
</div>
<s:iterator value="#attr.classes" var="clazz">
    <s:if test="#attr.clazz.categories.size > 0">
        <div id="categorySelect_${id}_${clazz.id}" class="select-category"
                style="${clazz.id == selectedClassId ? "" : "display: none;"}">
            <s:set var="disabled" value="%{#clazz.id != #selectedClassId}" />
            <s:select list="#clazz.categories" listKey="id" listValue="name" style="margin-top: 5px;"
                disabled="#disabled" cssClass="%{#categoryCssClass}">
                <s:param name="value">${selectedCategoryId}</s:param>
                <s:param name="name">${categoryField}</s:param>
            </s:select>
        </div>
    </s:if>
</s:iterator>
<s:iterator value="#attr.categories" var="category">
    <s:if test="#attr.category.subcategories.size > 0">
        <div id="subcategorySelect_${id}_${category.id}" class="select-subcategory"
                style="${category.id == selectedCategoryId ? "" : "display: none;"}">
            <s:set var="disabled" value="%{#category.id != #selectedCategoryId}" />
            <s:select list="#category.subcategories" listKey="id" listValue="name" headerKey="" headerValue=""
                style="margin-top: 5px;" disabled="#disabled" cssClass="%{#subcategoryCssClass}">
                <s:param name="value">${selectedSubcategoryId}</s:param>
                <s:param name="name">${subcategoryField}</s:param>
            </s:select>
        </div>
    </s:if>
</s:iterator>
<% // We need this input to explicitly send an empty value when selecting a category without subcategories. %>
<input id="emptySubcategory" type="hidden" name="${subcategoryField}" value="" disabled="true" />
