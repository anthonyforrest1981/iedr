package pl.nask.crs.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.SchedulingPattern;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.dao.SchedulerDAO;
import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulerRunningNicHandleException;
import pl.nask.crs.scheduler.exceptions.JobDuplicationNameException;
import pl.nask.crs.scheduler.jobs.AbstractJob;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.impl.ArtificialUsersRegistry;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

public class SchedulerCronImpl implements SchedulerCron {
    static final Logger log = Logger.getLogger(SchedulerCronImpl.class);

    private Scheduler scheduler = new Scheduler();
    private SchedulerJobsConfig schedulerJobsConfig = new SchedulerJobsConfig();
    private JobConfigValidator jobConfigValidator;
    private SchedulerDAO schedulerDao;
    private NicHandleDAO nicHandleDao;
    private UserDAO userDao;
    private JobRegistry jobRegistry;
    private ArtificialUsersRegistry artificialUsersRegistry;

    public SchedulerCronImpl(SchedulerDAOImpl schedulerDao, SchedulerListener listener, NicHandleDAO nicHandleDao,
            UserDAO userDao, ArtificialUsersRegistry artificialUsersRegistry) {
        this.schedulerDao = schedulerDao;
        this.nicHandleDao = nicHandleDao;
        this.userDao = userDao;
        this.artificialUsersRegistry = artificialUsersRegistry;
        scheduler.addSchedulerListener(listener);
    }

    public void setRegistry(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
        this.jobConfigValidator = new JobConfigValidator(jobRegistry);
    }

    @Override
    public boolean reload(AuthenticatedUser user) throws AccessDeniedException, NicHandleNotFoundException {
        return forceReload();
    }

    @Override
    public boolean forceReload() throws AccessDeniedException, NicHandleNotFoundException {
        if (scheduler.isStarted()) {
            updateJobsConfiguration();
            return true;
        }
        return false;
    }

    @Override
    public boolean start(AuthenticatedUser user) {
        try {
            updateJobsConfiguration();
            scheduler.start();
            log.info("Scheduler started.");
            return true;
        } catch (IllegalStateException|AccessDeniedException|NicHandleNotFoundException e) {
            log.debug("Start scheduler failure " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean stop(AuthenticatedUser user) {
        return forceStop();
    }

    @Override
    public boolean forceStop() {
        try {
            scheduler.stop();
            log.info("Scheduler stopped.");
            return true;
        } catch (IllegalStateException e) {
            log.debug("Stop scheduler failure " + e.getMessage());
            return false;
        }
    }

    @Override
    public void invoke(AuthenticatedUser user, String taskName)
            throws AccessDeniedException, NicHandleNotFoundException {
        AbstractJob task = jobRegistry.getTask(taskName);
        JobConfig jobConfig = schedulerDao.getJobConfigByName(taskName);
        AuthenticatedUser schedulerJobUser =
                artificialUsersRegistry.getSchedulerJobAuthenticatedUser(jobConfig.getRunningNicHandle());
        SchedulerTask st = new SchedulerTask(jobConfig, schedulerJobUser, task);
        scheduler.launch(st);
    }

    @Override
    public boolean isRunning() {
        return scheduler.isStarted();
    }

    private void updateJobsConfiguration() throws AccessDeniedException, NicHandleNotFoundException {
        List<JobConfig> jobsConfig = filterValidJobsConfigurations(schedulerDao.getJobConfigs());
        schedulerJobsConfig.updateConfiguration(jobsConfig);
        doSchedule();
        doReschedule();
        doDeschedule();
    }

    private List<JobConfig> filterValidJobsConfigurations(List<JobConfig> jobs) {
        List<JobConfig> jobsConfig = new ArrayList<JobConfig>();
        for (JobConfig job : jobs) {
            jobConfigValidator.validate(job);
            if (!job.isValid()) {
                logJobConfigError(job);
            } else {
                jobsConfig.add(job);
            }
        }
        return jobsConfig;
    }

    private void logJobConfigError(JobConfig job, String errorMessage) {
        job.setErrorMessage(errorMessage);
        logJobConfigError(job);
    }

    private void logJobConfigError(JobConfig job) {
        job.setActive(false);
        schedulerDao.updateJobConfig(job);
    }

    private void doSchedule() throws AccessDeniedException, NicHandleNotFoundException {
        List<JobConfig> jobsConfig = schedulerJobsConfig.getJobsConfigToSchedule();
        for (JobConfig currentConfig : jobsConfig) {
            AbstractJob newJob = jobRegistry.getTask(currentConfig.getName());
            AuthenticatedUser schedulerJobUser =
                    artificialUsersRegistry.getSchedulerJobAuthenticatedUser(currentConfig.getRunningNicHandle());
            SchedulerTask task = new SchedulerTask(currentConfig, schedulerJobUser, newJob);
            try {
                String id = scheduler.schedule(currentConfig.getSchedulePattern(), task);
                currentConfig.setScheduleId(id);
                logJobConfigActive(currentConfig);
            } catch (Exception e) {
                log.error("Error while scheduling job for execution: " + e.getMessage(), e);
                logJobConfigError(currentConfig, e.getMessage());
            }
        }
    }

    private void logJobConfigActive(JobConfig job) {
        job.setActive(true);
        job.clearErrorMessage();
        schedulerDao.updateJobConfig(job);
    }

    private void doDeschedule() {
        List<JobConfig> jobsConfig = schedulerJobsConfig.getJobsConfigToDeschedule();
        for (JobConfig current : jobsConfig) {
            try {
                scheduler.deschedule(current.getScheduleId());
                logJobConfigRemoved(current);
            } catch (Exception e) {
                log.error("Error while removing job from the scheduler: " + e.getMessage(), e);
                logJobConfigError(current, e.getMessage());
            }
        }
    }

    private void logJobConfigRemoved(JobConfig current) {
        log.info("Job configuration removed from the schedule: " + current);
    }

    private void doReschedule() {
        List<JobConfig> jobs = schedulerJobsConfig.getJobsConfigToReschedule();
        for (JobConfig current : jobs) {
            try {
                scheduler.reschedule(current.getScheduleId(), current.getSchedulePattern());
                logJobConfigActive(current);
            } catch (Exception e) {
                log.error("Reschedule scheduler failure " + e.getMessage(), e);
                logJobConfigError(current, e.getMessage());
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Job> findJobs(AuthenticatedUser user, JobSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return schedulerDao.findJobs(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Job> findJobsHistory(AuthenticatedUser user, JobSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return schedulerDao.findJobsHistory(criteria, offset, limit, sortBy);
    }

    private void checkDuplicates(String name) throws JobDuplicationNameException {
        JobConfig task = schedulerDao.getJobConfigByName(name);
        if (task != null) {
            throw new JobDuplicationNameException();
        }
    }

    private void validateSchedulePattern(String schedule) throws InvalidSchedulePatternException {
        if (!SchedulingPattern.validate(schedule)) {
            throw new InvalidSchedulePatternException();
        }
    }

    private void validateNicHandle(String runningNicHandle)
            throws NicHandleNotFoundException, InvalidSchedulerRunningNicHandleException {
        NicHandle nicHandle = nicHandleDao.get(runningNicHandle);
        if (nicHandle == null) {
            throw new NicHandleNotFoundException(runningNicHandle);
        }
        User user = userDao.get(nicHandle.getNicHandleId());
        if (user == null || !user.hasGroup(Level.Batch)) {
            throw new InvalidSchedulerRunningNicHandleException(runningNicHandle);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addJobConfig(AuthenticatedUser user, String name, String schedule, String runningNicHandle)
            throws JobDuplicationNameException, InvalidSchedulePatternException, NicHandleNotFoundException,
            InvalidSchedulerRunningNicHandleException {
        validateSchedulePattern(schedule);
        checkDuplicates(name);
        validateNicHandle(runningNicHandle);

        return schedulerDao.createJobConfig(name, schedule, runningNicHandle);
    }

    @Transactional(readOnly = true)
    @Override
    public List<JobConfig> getJobConfigs(AuthenticatedUser user) {
        return schedulerDao.getJobConfigs();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeJobConfig(AuthenticatedUser user, int id) {
        schedulerDao.deleteJobConfigById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public JobConfig getJobConfig(AuthenticatedUser user, int id) {
        return schedulerDao.getJobConfigById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyJobConfig(AuthenticatedUser user, int id, String schedulePattern, String runningNicHandle)
            throws InvalidSchedulePatternException, NicHandleNotFoundException,
            InvalidSchedulerRunningNicHandleException {
        validateSchedulePattern(schedulePattern);
        validateNicHandle(runningNicHandle);

        JobConfig jobConfig = schedulerDao.getJobConfigById(id);
        jobConfig.setSchedulePattern(schedulePattern);
        jobConfig.setRunningNicHandle(runningNicHandle);
        schedulerDao.updateJobConfig(jobConfig);
    }
}
