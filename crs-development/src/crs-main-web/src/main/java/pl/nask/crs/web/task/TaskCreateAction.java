package pl.nask.crs.web.task;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.JobRegistry;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulerRunningNicHandleException;
import pl.nask.crs.scheduler.exceptions.JobDuplicationNameException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.Level;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 *
 * @author Artur Kielak
 */
public class TaskCreateAction extends AuthenticatedUserAwareAction {
    private static final Logger log = Logger.getLogger(TaskCreateAction.class);
    private TaskWrapper taskWrapper;
    private SchedulerCron schedulerCron;

    private List<String> validTaskNames;

    public TaskWrapper getTaskWrapper() {
        return taskWrapper;
    }

    public void setTaskWrapper(TaskWrapper taskWrapper) {
        this.taskWrapper = taskWrapper;
    }

    public TaskCreateAction(SchedulerCron schedulerCron, JobRegistry jobRegistry) {
        this.schedulerCron = schedulerCron;
        this.validTaskNames = jobRegistry.getTaskNames();
    }

    @Override
    public String execute() {
        try {
            schedulerCron.addJobConfig(getUser(), taskWrapper.getName(), taskWrapper.getSchedule(),
                    taskWrapper.getRunningNicHandle());
            addActionMessage("Successfully added new task.");
            return SUCCESS;
        } catch (UserNotAuthenticatedException e) {
            addActionError("User not authenticated.");
            return ERROR;
        } catch (JobDuplicationNameException e) {
            addActionError("Task name duplicated.");
            return ERROR;
        } catch (InvalidSchedulePatternException e) {
            addActionError("Invalid schedule pattern.");
            return ERROR;
        } catch (InvalidSchedulerRunningNicHandleException e) {
            addActionError(String.format("Nic Handle should be in %s group", Level.Batch.getName()));
            return ERROR;
        } catch (NicHandleNotFoundException e) {
            addActionError("Nic Handle not found");
            return ERROR;
        } catch (Exception e) {
            addActionError("Exception " + e.getMessage());
            return ERROR;
        }
    }

    @Override
    public String input() throws Exception {
        taskWrapper = new TaskWrapper();
        return super.input();
    }

    public List<String> getValidTaskNames() {
        return validTaskNames;
    }
}
