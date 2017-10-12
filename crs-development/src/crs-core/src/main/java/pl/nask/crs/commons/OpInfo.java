package pl.nask.crs.commons;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class OpInfo {
    private String userName; // not empty
    private String superUserName; // if the operation is performed on behalf of the user
    private String remark;

    public OpInfo(AuthenticatedUser user) {
        this.userName = user.getUsername();
        this.superUserName = user.getSuperUserName();
    }

    public OpInfo(NicHandle nicHandle) {
        this.userName = nicHandle.getNicHandleId();
    }

    public OpInfo(AuthenticatedUser user, String remark) {
        this(user);
        this.remark = remark;
    }

    public OpInfo(NicHandle nicHandle, String remark) {
        this(nicHandle);
        this.remark = remark;
    }

    public static OpInfo withModifiedRemark(OpInfo opInfo, String remark) {
        return new OpInfo(opInfo.userName, opInfo.superUserName, remark);
    }

    protected OpInfo(String userName) {
        this.userName = userName;
    }

    protected OpInfo(String userName, String remark) {
        this(userName);
        this.remark = remark;
    }

    protected OpInfo(String userName, String superUserName, String remark) {
        this(userName, remark);
        this.superUserName = superUserName;
    }

    public String getRemark() {
        return remark;
    }

    public String getActorName() {
        return superUserName == null ? userName : superUserName;
    }

    public String getActorNameForRemark() {
        return superUserName == null ? userName : superUserName + " B/O " + userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getSuperUserName() {
        return superUserName;
    }
}
