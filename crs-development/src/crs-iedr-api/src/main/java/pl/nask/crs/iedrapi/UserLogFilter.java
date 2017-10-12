package pl.nask.crs.iedrapi;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.log4j.NDCAwareFileAppender;

public class UserLogFilter implements Filter {
    private final static Logger log = Logger.getLogger(UserLogFilter.class);

    @Override
    public void destroy() {
        NDCAwareFileAppender.clear();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // try to establish user log
        IedrApiConfig.getUserAwareAppender().initEventBuffer();
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            AuthData ad = AuthData.getInstance(session, null);
            if (ad.getUsername() != null) {
                IedrApiConfig.getUserAwareAppender().registerAppender(ad.getUsername());
            }
        }

        chain.doFilter(request, response);

        IedrApiConfig.getUserAwareAppender().unregisterAppender();

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
