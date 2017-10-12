package pl.nask.crs.scheduler.exceptions;

public class InvalidSchedulerRunningNicHandleException extends Exception {

    public InvalidSchedulerRunningNicHandleException(String nicHandle) {
        super(String.format("Provided Nic Handle (%s) has an invalid access level", nicHandle));
    }

}
