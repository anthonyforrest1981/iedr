package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface ReportsTestMethods {

    void findTotalDomains() throws AccessDeniedException;

    void findTotalDomainsPerDate() throws AccessDeniedException;

    void findTotalDomainsPerClass() throws AccessDeniedException;

    void searchForReports() throws AccessDeniedException;

}
