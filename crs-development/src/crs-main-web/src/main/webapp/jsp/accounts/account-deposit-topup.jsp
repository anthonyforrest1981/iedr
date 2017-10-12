<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>TopUp Deposi</title>
    <sx:head/>
</head>
<body>
<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="account_form" theme="simple">
    <n:group2 titleText="TopUp Deposit">
        <n:field2 label="Amount" fielderror="topUpAmount"><input type="text" name="topUpAmount" value="${topUpAmount}"/></n:field2>
        <n:field2 label="Remark" fielderror="remark"><input type="text" name="remark" size="50" value="${remark}"></n:field2>
    </n:group2>

    <s:hidden name="id"/>
    <center>
        <script>
            var formSubmitted = false;
        </script>
        <s:submit value="Submit" action="deposit-topup-topup" theme="simple"
            onclick="if (formSubmitted) return false; formSubmitted = true; this.value='Submitting...'; return true;"/>
        <input type="button" value="Back" onClick="history.back()">
    </center>
</s:form>

</body>
