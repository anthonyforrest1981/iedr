package pl.nask.crs.user.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.user.User;

public class UserSearchCriteria implements SearchCriteria<User> {

    private Integer nhLevelMask;
    private Boolean isInternal;

    public void setNhLevelMask(int nhLevelMask) {
        this.nhLevelMask = nhLevelMask;
    }

    public Integer getNhLevelMask() {
        return this.nhLevelMask;
    }

    public void setIsInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public Boolean getIsInternal() {
        return this.isInternal;
    }

}
