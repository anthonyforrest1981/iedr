package pl.nask.crs.accounts.search;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.SearchCriteria;

public class AccountSearchCriteria implements SearchCriteria<Account> {

    private String nicHandle;
    private Long id;
    private String name;
    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
