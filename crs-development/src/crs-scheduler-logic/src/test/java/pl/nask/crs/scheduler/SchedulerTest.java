package pl.nask.crs.scheduler;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;

@ContextConfiguration(locations = {"/scheduler-config.xml", "/scheduler-config-test.xml"})
public class SchedulerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    SchedulerDAOImpl dao;

    @Test
    public void getJobConfigByName() {
        JobConfig config = dao.getJobConfigByName("Updater");
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getId(), 10);
        Assert.assertEquals(config.getName(), "Updater");
        Assert.assertEquals(config.getSchedulePattern(), "*/10 * * * *");
        Assert.assertNull(config.getErrorMessage());
        Assert.assertNull(config.getScheduleId());
        Assert.assertTrue(config.isActive());
    }

    @Test
    public void getJobConfigById() {
        JobConfig config = dao.getJobConfigById(1);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getId(), 1);
        Assert.assertEquals(config.getName(), "PushQ");
        Assert.assertEquals(config.getSchedulePattern(), "00 12 * * * ");
        Assert.assertNull(config.getErrorMessage());
        Assert.assertNull(config.getScheduleId());
        Assert.assertTrue(config.isActive());
    }

    @Test
    public void getJobConfigs() {
        List<JobConfig> jobConfigs = dao.getJobConfigs();
        Assert.assertNotNull(jobConfigs);
        Assert.assertEquals(jobConfigs.size(), 18);
        Collections.sort(jobConfigs, new Comparator<JobConfig>() {
            @Override
            public int compare(JobConfig o1, JobConfig o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        Assert.assertEquals(jobConfigs.get(0).getId(), 1);
        Assert.assertEquals(jobConfigs.get(0).getName(), "PushQ");
        Assert.assertEquals(jobConfigs.get(0).getSchedulePattern(), "00 12 * * * ");
    }

    @Test
    public void updateConfigStatusTest() throws Exception {
        JobConfig jobConfig = dao.getJobConfigById(3);
        Assert.assertNull(jobConfig.getScheduleId());
        jobConfig.setScheduleId("scheduleId");
        dao.updateJobConfig(jobConfig);
        jobConfig = dao.getJobConfigById(3);
        Assert.assertEquals("scheduleId", jobConfig.getScheduleId());
    }

    @Test
    public void getJobByName() {
        Job job = dao.getJobByName("job1");
        Assert.assertNotNull(job);
        Assert.assertEquals((int) job.getId(), 1);
        Assert.assertEquals(job.getName(), "job1");
        Assert.assertEquals(job.getStatus(), JobStatus.ACTIVE);
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getStart()), "2014-10-13 10:10:30");
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getEnd()), "2014-10-13 10:10:30");
    }

    @Test
    public void getJobById() {
        Job job = dao.getJobById(1);
        Assert.assertNotNull(job);
        Assert.assertEquals((int) job.getId(), 1);
        Assert.assertEquals(job.getName(), "job1");
        Assert.assertEquals(job.getStatus(), JobStatus.ACTIVE);
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getStart()), "2014-10-13 10:10:30");
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getEnd()), "2014-10-13 10:10:30");
    }

    @Test
    public void getJobsByStatus() {
        List<Job> jobs = dao.getJobsByStatus(JobStatus.ACTIVE);
        Assert.assertNotNull(jobs, "getJobById()(test)");
        Assert.assertEquals(jobs.size(), 4);
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        Job job = jobs.get(0);
        Assert.assertEquals((int) job.getId(), 1);
        Assert.assertEquals(job.getName(), "job1");
        Assert.assertEquals(job.getStatus(), JobStatus.ACTIVE);
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getStart()), "2014-10-13 10:10:30");
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getEnd()), "2014-10-13 10:10:30");
    }

    @Test
    public void getJobs() {
        LimitedSearchResult<Job> jobsResult = dao.findJobs(new JobSearchCriteria(), 0, 10, null);
        List<Job> jobs = new ArrayList<>(jobsResult.getResults());
        Assert.assertEquals(jobsResult.getTotalResults(), 6);
        Assert.assertEquals(jobs.size(), 6);
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        Job job = jobs.get(0);
        Assert.assertEquals((int) job.getId(), 1);
        Assert.assertEquals(job.getName(), "job1");
        Assert.assertEquals(job.getStatus(), JobStatus.ACTIVE);
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getStart()), "2014-10-13 10:10:30");
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getEnd()), "2014-10-13 10:10:30");
    }

    @Test
    public void getJobsHist() {
        LimitedSearchResult<Job> jobsResult = dao.findJobsHistory(new JobSearchCriteria(), 0, 10, null);
        List<Job> jobs = new ArrayList<>(jobsResult.getResults());
        Assert.assertEquals(jobsResult.getTotalResults(), 5);
        Assert.assertEquals(jobs.size(), 5);
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        Job job = jobs.get(0);
        Assert.assertEquals((int) job.getId(), 1);
        Assert.assertEquals(job.getName(), "job1");
        Assert.assertEquals(job.getStatus(), JobStatus.ACTIVE);
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getStart()), "2014-10-13 10:10:31");
        Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(job.getEnd()), "2014-10-13 10:10:31");
    }

    @Test
    public void logJobStartedTest() {
        String jobName = "invoicing";
        dao.logJobStarted(jobName);
        Job job = dao.getJobByName(jobName);
        Assert.assertNotNull(job);
        Assert.assertEquals(job.getStatus(), JobStatus.RUNNING);
        Assert.assertTrue(job.getStart().getTime() - new Date().getTime() < 5000);
    }

}
