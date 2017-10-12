<%@ attribute name="titleText" description="Text to be displayed on header" %>
<%@ attribute name="cssIcon" description="Group icon css class" %>
<%@ attribute name="id" description="Element's html id" %>

<%
    if (titleText == null) titleText = "";
    if (cssIcon == null) cssIcon = "";
    String idAttribute = id == null ? "" : " id=\"" + id + "\"";
    String ua = request.getHeader("User-Agent");
    boolean isIE = false;

    if(ua != null) {
        // detect IE (refuse Netscape and Opera)
        isIE = (ua.indexOf("MSIE") != -1) && (ua.indexOf("Netscape") == -1) && (ua.indexOf("Opera") == -1);
    }

    response.setHeader("Vary", "User-Agent");
%>

<div<%= idAttribute %> class="group">

<%if (isIE) { %>

    <div class="GH">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="GHLC">&nbsp;</td>
                <td class="GHTB">
                    <% if (cssIcon != null) { %>
                        <div class="GHTB-icon ${cssIcon}">&nbsp;</div>
                    <% } %>
                    <div class="GHTB-title">${titleText}</div>
                </td>
                <td class="GHRC">&nbsp;</td>
            </tr>
        </table>
    </div>

    <div class="GC">
        <div class="GCCA"><jsp:doBody/></div>
    </div>

    <div class="GF">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="GFLC">&nbsp;</td>
                <td class="GFFB">&nbsp;</td>
                <td class="GFRC">&nbsp;</td>
            </tr>
        </table>
    </div>

<% } else { %>

    <div class="GH">
        <div class="GHLC"></div>
        <div class="GHRC"></div>
        <div class="GHTB">
            <div class="GHTB-bar">
                <% if (cssIcon != null) { %>
                    <div class="GHTB-icon">
                        <div class="${cssIcon}">&nbsp;</div>
                    </div>
                <% } %>
                <div class="GHTB-title">${titleText}</div>
            </div>
        </div>
    </div>

    <div class="GC">
        <div class="GCCA"><jsp:doBody/></div>
    </div>

    <div class="GF">
        <div class="GFLC"></div>
        <div class="GFRC"></div>
        <div class="GFFB"></div>
    </div>

<% } %>

</div>
