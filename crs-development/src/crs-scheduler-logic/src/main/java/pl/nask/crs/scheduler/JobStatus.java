package pl.nask.crs.scheduler;

public enum JobStatus {
    RUNNING("Running"), FAILED("Failed"), FINISHED("Finished"), ACTIVE("Active");

    private String name;

    JobStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static JobStatus forJobName(String name) {
        for (JobStatus s : values()) {
            if (s.getName().equals(name))
                return s;
        }
        throw new IllegalArgumentException("Unknown name for JobStatus: " + name);
    }
}
