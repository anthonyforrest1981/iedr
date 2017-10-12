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
        Force Process execution
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Run task" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Name" cssContainer="force-margin-left30 force-width30">
                <s:select list="validTaskNames" name="taskName" />
            </n:field>
            <n:fielderror fields="taskWrapper.name" colspan="1"/>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Run Task" theme="simple" action="task-run-runTask"/>
        </center>
    </s:form>
</n:group2>

<n:group2 titleText="Scheduler status" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Running" cssContainer="force-margin-left30 force-width30">
                ${schedulerRunning}
            </n:field>
        </center>
    </s:form>
</n:group2>
</body>
</html>