package pl.nask.crs.scheduler.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import de.neuland.jade4j.Jade4J;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.ServletUtils;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.*;
import pl.nask.crs.security.authentication.*;

public class SchedulerMonitor extends HttpServlet {

    public static final String SESSION_ATTRIBUTE_USER = "user";
    public static final String SYSTEM_DISCRIMINATOR = "scheduler";
    public static final String STATUS_UNKNOWN_FIELD_VALUE = "???";
    public static final String FLASH_ERROR = "flashError";
    private int MAX_INACTIVE_PERIOD_IN_SECONDS = 30 * 60;
    private Logger LOGGER = Logger.getLogger(SchedulerMonitor.class);
    private ApplicationContext springContext;
    private SchedulerCron schedulerCron;
    private JobRegistry jobRegistry;
    private AuthenticationService authenticationService;

    @Override
    public void init() throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        schedulerCron = (SchedulerCron) springContext.getBean("schedulerCron");
        jobRegistry = (JobRegistry) springContext.getBean("jobRegistry");
        authenticationService = (AuthenticationService) springContext.getBean("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            disableCache(resp);
            HttpSession session = req.getSession();
            switch (req.getServletPath()) {
                case "/schedulerStatus":
                    printStatus(resp);
                    break;
                case "/schedulerMonitor":
                    final AuthenticatedUser user = (AuthenticatedUser) session.getAttribute(SESSION_ATTRIBUTE_USER);
                    if (user == null) {
                        LOGGER.info("No valid session exists, redirecting to login screen");
                        resp.sendRedirect("schedulerMonitorAuth");
                    } else {
                        printDashboard(user, resp, req.getParameterMap().containsKey("refresh"));
                    }
                    break;
                case "/schedulerMonitorAuth":
                    if (session.getAttribute(SESSION_ATTRIBUTE_USER) != null) {
                        LOGGER.info("Valid session exists, redirecting to status screen");
                        resp.sendRedirect("schedulerMonitor");
                    } else {
                        String flashError = (String) session.getAttribute(FLASH_ERROR);
                        session.removeAttribute(FLASH_ERROR);
                        printLogin(resp, flashError);
                    }
                    break;
            }
        } catch (Exception e) {
            LOGGER.fatal("Cannot process request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            disableCache(resp);
            switch (req.getServletPath()) {
                case "/schedulerMonitor":
                    performAction(req, resp);
                    break;
                case "/schedulerMonitorAuth":
                    performLogin(req, resp);
                    break;
            }
        } catch (Exception e) {
            LOGGER.fatal("Cannot process request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void printStatus(HttpServletResponse resp) throws IOException {
        boolean isRunning = schedulerCron.isRunning();
        String response = "Scheduler status: " + (isRunning ? "ON" : "OFF");
        resp.getWriter().append(response);
    }

    private void printDashboard(AuthenticatedUser user, HttpServletResponse resp, boolean refresh)
            throws AccessDeniedException, IOException {
        boolean schedulerRunning = schedulerCron.isRunning();
        List<JobDesc> jobDescs = new ArrayList<>();
        Map<String, JobConfig> configsMap = prepareConfigsMap(schedulerCron.getJobConfigs(user));
        LimitedSearchResult<pl.nask.crs.scheduler.Job> schedulerJobs = schedulerCron.findJobs(user,
                new JobSearchCriteria(), 0, 100, null);
        Map<String, pl.nask.crs.scheduler.Job> schedulerJobsMap = prepareJobsMap(schedulerJobs.getResults());
        for (String jobName : jobRegistry.getTaskNames()) {
            final Job schedulerJob = schedulerJobsMap.get(jobName);
            final JobConfig jobConfig = configsMap.get(jobName);
            final String schedulePattern = jobConfig != null ? jobConfig.getSchedulePattern()
                    : STATUS_UNKNOWN_FIELD_VALUE;
            if (schedulerJob == null) {
                jobDescs.add(new JobDesc(jobName, schedulePattern, STATUS_UNKNOWN_FIELD_VALUE,
                        STATUS_UNKNOWN_FIELD_VALUE, STATUS_UNKNOWN_FIELD_VALUE));
            } else {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM HH:mm:ss");
                String startDate = schedulerJob.getStart() != null ? simpleDateFormat.format(schedulerJob.getStart())
                        : STATUS_UNKNOWN_FIELD_VALUE;
                String endDate = schedulerJob.getEnd() != null ? simpleDateFormat.format(schedulerJob.getEnd())
                        : STATUS_UNKNOWN_FIELD_VALUE;
                final JobDesc j = new JobDesc(jobName, schedulePattern, schedulerJob.getStatusName(), startDate,
                        endDate);
                jobDescs.add(j);
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("schedulerRunning", schedulerRunning);
        model.put("jobRunning", refresh || Iterables.any(jobDescs, new Predicate<JobDesc>() {
            @Override
            public boolean apply(JobDesc jobDesc) {
                return jobDesc.status == JobStatus.RUNNING.getName();
            }
        }));
        model.put("jobs", jobDescs);

        InputStream stream = getServletContext().getResourceAsStream("/templates/dashboard.jade");
        final String responseString = Jade4J.render(new InputStreamReader(stream), "dashboard.jade", model, true);
        resp.getWriter().append(responseString);
    }

    private void printLogin(HttpServletResponse resp, String errorMessage) throws IOException {
        Map<String, Object> model = new HashMap<>();
        if (errorMessage != null) {
            model.put("failureMessage", errorMessage);
        }
        InputStream stream = getServletContext().getResourceAsStream("/templates/login.jade");
        final String responseString = Jade4J.render(new InputStreamReader(stream), "login.jade", model, true);
        resp.getWriter().append(responseString);
    }

    private Map<String, pl.nask.crs.scheduler.Job> prepareJobsMap(List<Job> results) {
        Map<String, pl.nask.crs.scheduler.Job> result = new HashMap<>();
        for (Job j : results) {
            result.put(j.getName(), j);
        }
        return result;
    }

    private Map<String, JobConfig> prepareConfigsMap(List<JobConfig> results) {
        Map<String, JobConfig> result = new HashMap<>();
        for (JobConfig j : results) {
            result.put(j.getName(), j);
        }
        return result;
    }

    private void performAction(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, AccessDeniedException, NicHandleNotFoundException {
        String actionName = req.getParameter("action");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(SESSION_ATTRIBUTE_USER) == null) {
            LOGGER.error("Trying to perform action " + actionName
                    + " with invalid session, redirecting to login screen");
            resp.sendRedirect("schedulerMonitorAuth");
            return;
        }
        AuthenticatedUser user = (AuthenticatedUser) session.getAttribute(SESSION_ATTRIBUTE_USER);
        switch (actionName) {
            case "start":
                LOGGER.info("User " + user.getUsername() + " started scheduler");
                schedulerCron.start(user);
                resp.sendRedirect("schedulerMonitor");
                break;
            case "stop":
                LOGGER.info("User " + user.getUsername() + " stopped scheduler");
                schedulerCron.stop(user);
                resp.sendRedirect("schedulerMonitor");
                break;
            case "reload":
                LOGGER.info("User " + user.getUsername() + " reloaded scheduler config");
                schedulerCron.reload(user);
                resp.sendRedirect("schedulerMonitor");
                break;
            case "run":
                if (!schedulerCron.isRunning()) {
                    LOGGER.fatal("Invoking RUN action on stopped scheduler");
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "The process cannot be manually run if the scheduler is not running");
                } else {
                    String taskName = req.getParameter("job");
                    if (jobRegistry.getTaskNames().contains(taskName)) {
                        LOGGER.info("User " + user.getUsername() + " manually invoked scheduler job " + taskName);
                        schedulerCron.invoke(user, taskName);
                        resp.sendRedirect("schedulerMonitor?refresh");
                    } else {
                        LOGGER.fatal("Unknown job name " + taskName);
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown job name " + taskName
                                + ". Try reloading scheduler config.");
                    }
                }
                break;
            default:
                LOGGER.fatal("Unknown action " + actionName + " for schedulerMonitorAuth");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action " + actionName);
        }
    }

    private void performLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loginAction = req.getParameter("action");
        switch (loginAction) {
            case "login": {
                String username = req.getParameter("nichandle");
                String password = req.getParameter("password");
                HttpSession session = req.getSession();
                final AuthenticatedUser oldUser = (AuthenticatedUser) session.getAttribute(SESSION_ATTRIBUTE_USER);
                if (oldUser != null) {
                    LOGGER.warn("Logging in as " + username + " but there is already a session for "
                            + oldUser.getUsername() + ". Old session will be overwritten with new credentials.");
                }

                try {
                    AuthenticatedUser user = authenticationService.authenticate(username, password, false,
                            ServletUtils.getRemoteIP(req), false, null, false, SYSTEM_DISCRIMINATOR);
                    LOGGER.info("User " + user.getUsername() + " successfully logged into scheduler monitor");
                    session.setMaxInactiveInterval(MAX_INACTIVE_PERIOD_IN_SECONDS);
                    session.setAttribute(SESSION_ATTRIBUTE_USER, user);
                    resp.sendRedirect("schedulerMonitor");
                } catch (LoginException e) {
                    LOGGER.error("Failed to log in as " + username, e);
                    session.setAttribute(FLASH_ERROR, "Invalid username or password");
                    resp.sendRedirect("schedulerMonitorAuth");
                } catch (AuthenticationException e) {
                    LOGGER.error("User " + username + " forbidden from logging into the service", e);
                    session.setAttribute(FLASH_ERROR, "Invalid username or password");
                    resp.sendRedirect("schedulerMonitorAuth");
                }
                break;
            }
            case "logout": {
                HttpSession session = req.getSession(false);
                if (session != null) {
                    final AuthenticatedUser user = (AuthenticatedUser) session.getAttribute(SESSION_ATTRIBUTE_USER);
                    if (user != null) {
                        LOGGER.info("User " + user.getUsername() + " successfully logged out of scheduler monitor");
                    }
                    session.invalidate();
                }
                resp.sendRedirect("schedulerMonitorAuth");
                break;
            }
            default:
                LOGGER.fatal("Unknown action " + loginAction + " for schedulerMonitorAuth");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action " + loginAction);
        }
    }

    private void disableCache(HttpServletResponse resp) {
        // Set standard HTTP/1.0 no-cache header
        resp.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache headers
        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate, max-age=0");
        // Set IE extended HTTP/1.1 no-cache headers
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
    }

    @Override
    public void destroy() {
        super.destroy();
        schedulerCron.forceStop();
    }

    public static class JobDesc {
        public JobDesc(String name, String runline, String status, String startDate, String endDate) {
            this.name = name;
            this.runline = runline;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String name;
        public String runline;
        public String status;
        public String startDate;
        public String endDate;
    }
}
