package pl.nask.crs.app.email;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface EmailTemplateAppService extends AppSearchService<EmailTemplate, EmailTemplateSearchCriteria> {

    @Override
    LimitedSearchResult<EmailTemplate> search(AuthenticatedUser user, EmailTemplateSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    EmailTemplate get(AuthenticatedUser user, int id) throws AccessDeniedException;

    EmailTemplate saveEditableFields(AuthenticatedUser user, EmailTemplate emailTemplate) throws AccessDeniedException;

}
