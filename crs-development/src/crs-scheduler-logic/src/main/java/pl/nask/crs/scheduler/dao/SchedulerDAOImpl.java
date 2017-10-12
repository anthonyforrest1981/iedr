package pl.nask.crs.scheduler.dao;

import java.util.*;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.scheduler.Job;
import pl.nask.crs.scheduler.JobConfig;
import pl.nask.crs.scheduler.JobSearchCriteria;
import pl.nask.crs.scheduler.JobStatus;

public class SchedulerDAOImpl extends GenericIBatisDAO<Job, String> implements SchedulerDAO {

    private static Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "id");
        sortMap.put("name", "name");
        sortMap.put("start", "start");
        sortMap.put("end", "end");
        sortMap.put("status", "status");
    }

    public SchedulerDAOImpl() {
        setSortMapping(sortMap);
    }

    @Override
    public List<JobConfig> getJobConfigs() {
        List<JobConfig> result = performQueryForList("scheduler.getJobConfigs");
        return result;
    }

    @Override
    public Job getJobById(Integer jobId) {
        Job result = performQueryForObject("scheduler.getJobById", jobId);
        return result;
    }

    @Override
    public Job getJobByName(String jobName) {
        Job result = performQueryForObject("scheduler.getJobByName", jobName);
        return result;
    }

    @Override
    public JobConfig getJobConfigByName(String jobName) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", jobName);
        Object result = performQueryForObject("scheduler.getJobConfigByName", params);
        return (JobConfig) result;
    }

    @Override
    public JobConfig getJobConfigById(Integer jobId) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", jobId);
        Object result = performQueryForObject("scheduler.getJobConfigById", params);
        return (JobConfig) result;
    }

    @Override
    public LimitedSearchResult<Job> findJobs(JobSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        return performFind("scheduler.findJobs", "scheduler.findJobsCount", criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Job> findJobsHistory(JobSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        return performFind("scheduler.findJobsHist", "scheduler.findJobsHistCount", criteria, offset, limit, sortBy);
    }

    @Override
    public List<Job> getJobsByStatus(JobStatus status) {
        List<Job> result = performQueryForList("scheduler.getJobsByStatus", Collections.singletonMap("status", status));
        return result;
    }

    @Override
    public int createJobConfig(String name, String schedulePattern, String runningNicHandle) {
        JobConfig job = new JobConfig(name, schedulePattern, runningNicHandle);
        Integer result = performInsert("scheduler.createJobConfig", job);
        return result;
    }

    @Override
    public void deleteJobConfigById(int id) {
        performDelete("scheduler.deleteJobConfigById", id);
    }

    @Override
    public void deleteJobConfigByName(String name) {
        performDelete("scheduler.deleteJobConfigByName", name);
    }

    @Override
    public void updateJobConfig(JobConfig jobConfig) {
        performUpdate("scheduler.updateJobConfig", jobConfig);
    }

    public void logJobStarted(String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobName", jobName);
        map.put("start", new Date());
        map.put("status", JobStatus.RUNNING);
        performInsert("scheduler.createJobHist", jobName);
        performInsert("scheduler.createJob", map);
    }

    public void logJobSucceeded(String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobName", jobName);
        map.put("end", new Date());
        map.put("status", JobStatus.FINISHED);
        performUpdate("scheduler.updateJobStatus", map);
    }

    public void logJobFailed(String jobName, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobName", jobName);
        map.put("end", new Date());
        map.put("status", JobStatus.FAILED);
        map.put("error", message);
        performUpdate("scheduler.updateJobStatus", map);
    }
}
