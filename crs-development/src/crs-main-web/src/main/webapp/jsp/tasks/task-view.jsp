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
        Task View
    </title>
</head>
<body>

<s:set var="tasks" value="taskList"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Valid Tasks">
    <display:table name="tasks" id="taskRow" class="result">
        <display:column title="ID" property="id"/>
        <display:column title="Name" property="name"/>
        <display:column title="Schedule Pattern" property="schedulePattern"/>
        <display:column title="Running Nic Handle" property="runningNicHandle"/>
        <s:if test="%{hasPermission('task.update')}">
        <display:column title="" >
            <s:div cssClass="ticket-row-images">
                <s:url var="editUrl" action="task-edit-input" includeParams="none">
                    <s:param name="id">${taskRow.id}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="Edit" title="Edit"/>
                </s:a>
            </s:div>
        </display:column>
        </s:if>
        <s:if test="%{hasPermission('task.delete')}">
        <display:column title="">
            <s:div cssClass="ticket-row-images">
                <s:url var="removeTask" action="task-view-remove" includeParams="none">
                    <s:param name="id">${taskRow.id}</s:param>
                </s:url>
                <s:a href="%{removeTask}">
                    <img src="images/skin-default/action-icons/delete.png" alt="Remove" title="Remove"/>
                </s:a>
            </s:div>
        </display:column>
        </s:if>
    </display:table>
</n:group2>

<hr class="buttons"/>
<center>
    <input type="button" value="Back" onClick="history.back()">
</center>
</body>
</html>