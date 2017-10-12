package pl.nask.crs.web.domains;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.utils.JsonResponse;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesException;
import pl.nask.crs.domains.exceptions.BulkExportAuthCodesTotalException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.SessionStorageAction;

public class DomainExportAuthCodesAction extends GenericSearchAction<Domain, DomainSearchCriteria> implements
        SessionStorageAction {

    private DomainAppService domainAppService;

    public Set<String> getSelected() {
        return selected;
    }

    public void setSelected(Set<String> selected) {
        this.selected = selected;
    }

    private Set<String> selected;

    public List<String> getSelectedDomains() {
        return selectedDomains;
    }

    public void setSelectedDomains(List<String> selectedDomains) {
        this.selectedDomains = selectedDomains;
    }

    private List<String> selectedDomains;

    public DomainExportAuthCodesAction(final DomainAppService domainAppService) {
        super(new AppSearchService<Domain, DomainSearchCriteria>() {
            public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return domainAppService.search(user, criteria, offset, limit, orderBy);
            }
        });
        this.domainAppService = domainAppService;
        selected = new TreeSet<String>();
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        return new DomainSearchCriteria();
    }

    public String export() {
        if (selected == null || selected.isEmpty()) {
            addActionError("No domains selected!");
            return ERROR;
        }
        try {
            domainAppService.bulkExportAuthCodes(getUser(), Lists.newArrayList(selected));
        } catch (BulkExportAuthCodesException e) {
            addActionMessage("Emails sent for " + e.getDomainCount() + " out of " + e.getTotalDomainCount()
                    + " domains");
            addActionError(e.getMessage());
            return ERROR;
        } catch (BulkExportAuthCodesTotalException e) {
            addActionError(e.getMessage());
            return ERROR;
        } catch (Exception e) {
            addActionError("Sending email failure: " + e.getMessage());
            return ERROR;
        }
        selected.clear();
        addActionMessage("Emails sent successfully");
        return SUCCESS;
    }

    public String addToStorage() throws IOException {
        if (selected == null)
            selected = new TreeSet<String>();
        if (selectedDomains != null)
            selected.addAll(selectedDomains);
        return JsonResponse.OK();
    }

    public String removeFromStorage() throws IOException {
        if (selected == null)
            selected = new TreeSet<String>();
        if (selectedDomains != null)
            selected.removeAll(selectedDomains);
        return JsonResponse.OK();
    }

    public String getAllSelectedAsString() {
        return Joiner.on(",").join(selected);
    }

}
