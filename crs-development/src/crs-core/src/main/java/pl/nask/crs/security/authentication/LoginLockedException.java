package pl.nask.crs.security.authentication;

public class LoginLockedException extends LoginException {

    private long lockTimeInSeconds;

    public LoginLockedException(long lockTimeInSeconds) {
        super("Login Locked :" + lockTimeInSeconds);
        this.lockTimeInSeconds = lockTimeInSeconds;
    }

    public long getLockTimeInSeconds() {
        return lockTimeInSeconds;
    }
}
