package pl.nask.crs.web.domains;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class BulkTransferViewAction extends AuthenticatedUserAwareAction {
    private final BulkTransferAppService service;

    // bulk transfer id
    private long id;

    private BulkTransferRequest transferRequest;

    private String newDomains;
    private String domainName;

    public BulkTransferViewAction(BulkTransferAppService service) {
        this.service = service;
    }

    //action
    public String addDomains() throws AccessDeniedException {
        List<String> domains = splitDomains(newDomains);
        if (domains.isEmpty()) {
            addFieldError("newDomains", "A list of domains is expected");
            return ERROR;
        } else {
            try {
                service.addDomains(getUser(), id, domains);
                return SUCCESS;
            } catch (BulkTransferValidationException e) {
                addFieldError("newDomains", e.getValidationErrors().toString());
                return ERROR;
            }
        }
    }

    //action
    public String removeDomain() throws AccessDeniedException {
        try {
            service.removeDomain(getUser(), id, domainName);
            return SUCCESS;
        } catch (BulkTransferValidationException e) {
            addActionError(e.getValidationErrors().toString());
            return ERROR;
        }
    }

    public String closeRequest() throws AccessDeniedException {
        try {
            service.closeTransferRequest(getUser(), id);
            return SUCCESS;
        } catch (BulkTransferValidationException e) {
            addActionError(e.getValidationErrors().toString());
            return ERROR;
        }
    }

    public String forceCloseRequest() throws AccessDeniedException {
        service.forceCloseTransferRequest(getUser(), id);
        return SUCCESS;
    }

    public String transferAll() throws BulkTransferException, AccessDeniedException {
        try {
            service.transferAll(getUser(), id);
            return SUCCESS;
        } catch (BulkTransferValidationException e) {
            addActionError(e.getValidationErrors().toString());
            return ERROR;
        } catch (DefaultsNotFoundException | AccountNotFoundException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
    }

    public String transferValid() throws BulkTransferException, AccessDeniedException {
        try {
            service.transferValid(getUser(), id);
            return SUCCESS;
        } catch (BulkTransferValidationException e) {
            addActionError(e.getValidationErrors().toString());
            return ERROR;
        } catch (DefaultsNotFoundException | AccountNotFoundException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
    }

    private List<String> splitDomains(String list) {
        List<String> res = new ArrayList<String>();
        if (list != null) {
            String[] sl = list.split(",");
            for (String s : sl) {
                s = s.trim();
                if (s.length() != 0) {
                    res.add(s);
                }
            }
        }

        return res;
    }

    public BulkTransferRequest getTransferRequest() throws AccessDeniedException {
        if (transferRequest == null) {
            transferRequest = service.getTransferRequest(getUser(), id);
        }
        return transferRequest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewDomains() {
        return newDomains;
    }

    public void setNewDomains(String newDomains) {
        this.newDomains = newDomains;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
