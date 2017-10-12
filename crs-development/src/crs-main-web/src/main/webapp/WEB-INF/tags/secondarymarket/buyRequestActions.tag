<%@ attribute name="buyRequest" type="pl.nask.crs.secondarymarket.BuyRequest"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:div cssClass="ticket-row-images">
    <s:url var="checkoutUrl" action="buy-requests-browser-checkout" includeParams="none">
        <s:param name="id">${buyRequest.id}</s:param>
    </s:url>
    <s:url var="reviseUrl" action="buy-request-revise-input" includeParams="none">
        <s:param name="buyRequestId">${buyRequest.id}</s:param>
        <s:param name="previousAction">buy-requests-search</s:param>
    </s:url>
    <s:url var="viewUrl" action="buy-request-view-view" includeParams="none">
        <s:param name="buyRequestId">${buyRequest.id}</s:param>
        <s:param name="previousAction">buy-requests-search</s:param>
    </s:url>

    <s:if test="allowCheckout(#attr.buyRequest) && hasPermission('buyRequest.checkout')">
        <s:a href="%{checkoutUrl}">
            <img src="images/skin-default/action-icons/ticket.checkout.png" alt="Check Out" title="Check Out" />
        </s:a>
    </s:if>
    <s:elseif test="allowCheckin(#attr.buyRequest) && hasPermission('buyRequest.checkin')">
        <img src="images/skin-default/action-icons/ticket.checkin.png" alt="Check In"
            title="Check In" id="openCheckInDialog${buyRequest.id}" />
    </s:elseif>
    <s:if test="allowReassign(#attr.buyRequest) && hasPermission('buyRequest.reassign')">
        <img src="images/skin-default/action-icons/ticket.reassing.png" alt="Reassign"
            title="Reassign" id="openReassignDialog${buyRequest.id}" />
    </s:if>
    <s:if test="allowRevise(#attr.buyRequest) && hasPermission('buyRequest.revise')">
        <s:a href="%{reviseUrl}">
            <img src="images/skin-default/action-icons/ticket.revise.png" alt="Revise and Edit"
                 title="Revise and Edit" />
        </s:a>
    </s:if>
    <s:else>
        <s:if test="hasPermission('buyRequest.view')">
            <s:a href="%{viewUrl}">
                <img src="images/skin-default/action-icons/ticket.png" alt="View" title="View" />
            </s:a>
        </s:if>
    </s:else>
</s:div>
