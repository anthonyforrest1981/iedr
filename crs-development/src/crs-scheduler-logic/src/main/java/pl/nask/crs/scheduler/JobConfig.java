package pl.nask.crs.scheduler;

import pl.nask.crs.commons.utils.Validator;

public class JobConfig {
    private int id;
    private String name;
    private String schedulePattern;
    private String scheduleId;
    private String errorMessage;
    private boolean active;
    private String runningNicHandle;

    public JobConfig() {}

    public JobConfig(String name, String schedulePattern, String runningNicHandle) {
        this.name = name;
        this.schedulePattern = schedulePattern;
        this.runningNicHandle = runningNicHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedulePattern() {
        return schedulePattern;
    }

    public void setSchedulePattern(String schedulePattern) {
        this.schedulePattern = schedulePattern;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String schedulerId) {
        this.scheduleId = schedulerId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return Validator.isEmpty(errorMessage);
    }

    public void addErrorMessage(String reason) {
        if (Validator.isEmpty(errorMessage)) {
            errorMessage = reason;
        } else {
            errorMessage = errorMessage + ", " + reason;
        }
    }

    public void clearErrorMessage() {
        errorMessage = null;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRunningNicHandle() {
        return runningNicHandle;
    }

    public void setRunningNicHandle(String runningNicHandle) {
        this.runningNicHandle = runningNicHandle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JobConfig other = (JobConfig) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }
}
