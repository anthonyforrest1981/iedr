<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Autorenews</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="autorenews-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.domainName" theme="simple"
                                     cssStyle="width:150px;text-indent:0;" />
                    </n:field2>
                </div>
            </div>
              <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Billing Contact NH" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.billingNH"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:34%">
                <div>
                    <n:field2 label="Renewal Mode" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:10%;"
                              cssCtrlStyle="float:left;width:90%;">
                        <s:select name="searchCriteria.renewalMode" list="renewalModes"
                                  listKey="code" listValue="description"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;" />
                    </n:field2>
                </div>
            </div>
        </div>
          <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Renewal From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:65%;"
                              cssCtrlStyle="float:left;width:35%;">
                        <n:datefield2 field="searchCriteria.renewalFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:5%;"
                              cssCtrlStyle="float:left;width:95%;">
                        <n:datefield2 field="searchCriteria.renewalTo"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result"
                   requestURI="" sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Domain Name" property="name" sortable="true"/>
        <display:column title="BillC NH" property="billingContact.nicHandle" sortable="true" sortProperty="billingNH"/>
        <display:column title="BillC Name" property="billingContact.name" sortable="false"/>
        <display:column title="Autorenew Status" property="dsmState.renewalMode.description" sortable="true"/>
        <display:column title="Renewal Date" sortable="true" sortProperty="renewalDate">
            <s:date name="#attr.domainRow.renewalDate" format="dd/MM/yyyy"/>
        </display:column>
    </display:table>
</n:group2>

</body>
</html>