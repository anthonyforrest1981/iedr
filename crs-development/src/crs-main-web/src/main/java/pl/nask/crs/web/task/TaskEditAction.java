package pl.nask.crs.web.task;

import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.JobConfig;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulerRunningNicHandleException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Level;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class TaskEditAction extends AuthenticatedUserAwareAction {

    private SchedulerCron schedulerCron;
    private Integer id;
    private TaskWrapper wrapper;

    public TaskEditAction(SchedulerCron schedulerCron) {
        this.schedulerCron = schedulerCron;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(TaskWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String input() throws Exception {
        JobConfig jobConfig = schedulerCron.getJobConfig(getUser(), id);
        wrapper = new TaskWrapper(jobConfig);
        return INPUT;
    }

    public String save() throws AccessDeniedException {
        try {
            schedulerCron.modifyJobConfig(getUser(), wrapper.getId(), wrapper.getSchedule(),
                    wrapper.getRunningNicHandle());
            return SUCCESS;
        } catch (InvalidSchedulePatternException e) {
            addActionError("Invalid schedule pattern");
            return ERROR;
        } catch (InvalidSchedulerRunningNicHandleException e) {
            addActionError(String.format("Nic Handle should be in %s group", Level.Batch.getName()));
            return ERROR;
        } catch (NicHandleNotFoundException e) {
            addActionError("Nic Handle not found");
            return ERROR;
        }
    }
}
