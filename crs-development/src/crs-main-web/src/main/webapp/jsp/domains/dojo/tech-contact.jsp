<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="error!=null">
<div class="invalid-field">
    <div class="invalid-field-icon">&nbsp;</div>
    <div class="invalid-field-description">
        ${error}
    </div>
</div>
</s:if>

<script type="text/javascript">
    selectDefNicHandle('${defaultTechContact}', 'techContact')
</script>
