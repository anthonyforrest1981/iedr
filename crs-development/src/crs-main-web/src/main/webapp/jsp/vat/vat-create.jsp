<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>
        Create vat rate
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Crate vat rate" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Category" cssContainer="force-margin-left30 force-width30">
                <s:select list="categories" name="wrapper.category"
                          theme="simple" cssStyle="width:50%;"/>
            </n:field>
            <n:fielderror fields="wrapper.category" colspan="1"/>
            <n:field label="From Date" cssContainer="force-margin-left30 force-width30">
                <%--<input type="text" name="wrapper.fromDate" value="${wrapper.fromDate}"/>--%>
                <n:datefield field="wrapper.fromDate"/>
            </n:field>
            <n:fielderror fields="wrapper.fromDate" colspan="1"/>
            <n:field label="Vat Rate (ie 0.23)" cssContainer="force-margin-left30 force-width30">
                <s:textfield name="wrapper.vatRate"/>
            </n:field>
            <n:fielderror fields="wrapper.vatRate" colspan="1"/>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Create" theme="simple" action="vatCreate-create"/>
        </center>
    </s:form>
</n:group2>
</body>
</html>