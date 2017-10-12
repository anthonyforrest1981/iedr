package pl.nask.crs.commons.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    public static String getRemoteIP(HttpServletRequest request) {
        String addr = request.getHeader("X-FORWARDED-FOR");
        if (addr != null) {
            addr = addr.split(",")[0];
        } else {
            addr = request.getRemoteAddr();
        }
        return addr;
    }
}
