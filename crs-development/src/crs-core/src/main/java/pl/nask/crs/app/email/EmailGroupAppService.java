package pl.nask.crs.app.email;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface EmailGroupAppService extends AppSearchService<EmailGroup, EmailGroupSearchCriteria> {

    @Override
    LimitedSearchResult<EmailGroup> search(AuthenticatedUser user, EmailGroupSearchCriteria searchCriteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    EmailGroup get(AuthenticatedUser user, long id) throws AccessDeniedException;

    void update(AuthenticatedUser user, EmailGroup group) throws AccessDeniedException;

    void create(AuthenticatedUser user, EmailGroup group) throws AccessDeniedException;

    void delete(AuthenticatedUser user, EmailGroup group) throws AccessDeniedException;

    List<EmailGroup> getAllGroups(AuthenticatedUser user) throws AccessDeniedException;
}
