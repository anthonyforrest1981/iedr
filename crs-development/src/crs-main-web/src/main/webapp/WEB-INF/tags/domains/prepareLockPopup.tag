<%@ attribute name="domain" required="true" type="pl.nask.crs.app.domains.wrappers.DomainWrapper" %>
<%@ attribute name="isLocked" required="true" type="java.lang.Boolean" %>
<%@ attribute name="alreadyInLockingService" required="true" type="java.lang.Boolean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "changeLock";
    String openTriggerId = "openChangeLockDialog";
%>

<s:if test="%{#attr.isLocked}" >
    <c:set var="formAction" value="domainLocking-unlock.action" />
    <c:set var="hostmasterDefaultMessage" value="Unlocking domain" />
</s:if>
<s:else>
    <c:set var="formAction" value="domainLocking-lock.action" />
    <c:set var="hostmasterDefaultMessage" value="Locking domain" />
</s:else>

<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })

    function showError(flderr, show) {
        if (show) {
            flderr.style.display = "";
            return false;
        } else {
            flderr.style.display = "none";
            return true;
        }
    }

    function validateEmpty(fld, flderr) {
        return showError(flderr, fld.value.length == 0);
    }

    function validateFormOnSubmit(theForm) {
        var valid = validateEmpty(theForm.locking_hostmastersRemark, document.getElementById("locking_hostmastersRemark_fielderror"));
        return valid;
    }
</script>

<div id="<%=dialogId%>" class="jqmWindow">
    <n:group2 titleText="${hostmasterDefaultMessage}" cssIcon="group-icon-status">
        <form action="${formAction}" method="post" onsubmit="return validateFormOnSubmit(this);">
            <center style="margin: 10px 0">
                <s:if test="%{#attr.isLocked}">
                    Unlocking ${domain.name}
                </s:if>
                <s:else>
                    Locking ${domain.name}
                </s:else>
            </center>
            <s:if test="%{not #attr.alreadyInLockingService}">
                <center style="margin: 10px 0">
                    <strong>Warning: </strong>Locking the domain will turn on <em>locking service</em>.
                </center>
            </s:if>

            <input type="hidden" name="domainName" value="${domain.name}"/>
            <div class="ctrl-container" style="width:70%;height:8em;margin-left:10%">
                <div class="ctrl-label">Hostmaster's remark:</div>
                <div class="ctrl-field" style="width:50%;">
                    <textarea id="locking_hostmastersRemark" name="hostmastersRemark" rows="2" cols="20">${hostmasterDefaultMessage}</textarea>
                </div>
            </div>
            <div class="invalid-field" id="locking_hostmastersRemark_fielderror" style="display:none;">
                <div class="invalid-field-icon"></div>
                <div class="invalid-field-description"> You must enter a value for Hostmasters's Remark</div>
            </div>

            <s:if test="%{#attr.isLocked}">
                <div style="width:70%;margin-left:10%" class="ctrl-container">
                    <div style="width: 50%; padding-left: 115px;" class="ctrl-field">
                        <input type="hidden" name="__checkbox_disableLockingService" value="false">
                        <input type="checkbox" name="disableLockingService" id="locking_disableLocking" value="true">
                        <label for="locking_disableLocking">Disable locking service</label>
                    </div>
                </div>
            </s:if>

            <div style="clear:both; height:0;">&nbsp;</div>
            <hr class="buttons"/>

            <center>
                <input type="Submit" value="Submit"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>
        </form>
    </n:group2>
</div>

