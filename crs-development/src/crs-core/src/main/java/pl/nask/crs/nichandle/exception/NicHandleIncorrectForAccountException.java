package pl.nask.crs.nichandle.exception;

public class NicHandleIncorrectForAccountException extends NicHandleException {
    private String nicHandleId;
    private long accountId;

    public String getNicHandleId() {
        return nicHandleId;
    }

    public long getAccountId() {
        return accountId;
    }

    public NicHandleIncorrectForAccountException(String nicHandleId, long accountId) {
        super("Incorrect Nic handle: " + nicHandleId + " for the account: " + accountId);
        this.nicHandleId = nicHandleId;
        this.accountId = accountId;
    }
}
