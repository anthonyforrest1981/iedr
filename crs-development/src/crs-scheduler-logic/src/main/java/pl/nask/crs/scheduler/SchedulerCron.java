package pl.nask.crs.scheduler;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulerRunningNicHandleException;
import pl.nask.crs.scheduler.exceptions.JobDuplicationNameException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface SchedulerCron {
    boolean start(AuthenticatedUser user) throws AccessDeniedException;

    boolean stop(AuthenticatedUser user) throws AccessDeniedException;

    boolean reload(AuthenticatedUser user) throws AccessDeniedException, NicHandleNotFoundException;

    boolean forceReload() throws AccessDeniedException, NicHandleNotFoundException;

    boolean forceStop();

    void invoke(AuthenticatedUser user, String taskName) throws AccessDeniedException, NicHandleNotFoundException;

    boolean isRunning();

    LimitedSearchResult<Job> findJobs(AuthenticatedUser user, JobSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<Job> findJobsHistory(AuthenticatedUser user, JobSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    Integer addJobConfig(AuthenticatedUser user, String name, String schedule, String runningNicHandle)
            throws AccessDeniedException, JobDuplicationNameException, InvalidSchedulePatternException,
            NicHandleNotFoundException, InvalidSchedulerRunningNicHandleException;

    void removeJobConfig(AuthenticatedUser user, int id) throws AccessDeniedException;

    void modifyJobConfig(AuthenticatedUser user, int id, String schedulePattern, String runningNicHandle)
            throws AccessDeniedException, InvalidSchedulePatternException, NicHandleNotFoundException,
            InvalidSchedulerRunningNicHandleException;

    JobConfig getJobConfig(AuthenticatedUser user, int id) throws AccessDeniedException;

    List<JobConfig> getJobConfigs(AuthenticatedUser user) throws AccessDeniedException;
}
