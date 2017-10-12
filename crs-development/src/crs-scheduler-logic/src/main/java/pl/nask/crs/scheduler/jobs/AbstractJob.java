package pl.nask.crs.scheduler.jobs;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.log4j.EventObserver;
import pl.nask.crs.commons.log4j.NDCAwareFileAppender;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public abstract class AbstractJob {
    private static final Logger LOG = Logger.getLogger(AbstractJob.class);

    private static NDCAwareFileAppender APPENDER = (NDCAwareFileAppender) Logger.getRootLogger().getAppender(
            "processAware");

    public final void run(EventObserver observer, AuthenticatedUser user) throws Exception {
        try {
            registerAppender(observer);
            LOG.info("Starting job: " + getJobName());
            runJob(user);
            LOG.info("Job ended: " + getJobName());
        } finally {
            unregisterAppender();
        }
    }

    public abstract void runJob(AuthenticatedUser user) throws Exception;

    public abstract String getJobName();

    private void registerAppender(EventObserver observer) {
        APPENDER.registerAppender(getJobName(), observer);
    }

    private void unregisterAppender() {
        APPENDER.unregisterAppender();
    }
}
