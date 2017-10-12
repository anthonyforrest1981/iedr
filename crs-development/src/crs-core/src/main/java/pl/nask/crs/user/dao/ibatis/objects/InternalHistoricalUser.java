package pl.nask.crs.user.dao.ibatis.objects;

import java.util.Date;
import java.util.List;

public class InternalHistoricalUser extends InternalUser {

    private Date changeDate;
    private String changedBy;
    private long changeId;

    public InternalHistoricalUser() {}

    public InternalHistoricalUser(String username, String password, int level, boolean internal, String salt,
            Date passwordChangeDate, boolean forcePasswordChange, boolean useTwoFactorAuthentication, String secret,
            String name, List<String> permissionNames, Date changeDate, String changedBy) {
        super(username, password, level, internal, salt, passwordChangeDate, forcePasswordChange,
                useTwoFactorAuthentication, secret, name, permissionNames);
        this.changeDate = changeDate;
        this.changedBy = changedBy;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

}
