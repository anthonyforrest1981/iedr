package pl.nask.crs.app.domains;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface BulkTransferAppService {

    public long createBulkTransferProcess(AuthenticatedUser user, long losingAccount, long gainingAccount,
            String remarks) throws AccessDeniedException;

    public void addDomains(AuthenticatedUser user, long bulkTransferId, List<String> domainNames)
            throws BulkTransferValidationException, AccessDeniedException;

    public List<BulkTransferRequest> findTransfers(AuthenticatedUser user) throws AccessDeniedException;

    public BulkTransferRequest getTransferRequest(AuthenticatedUser user, long id) throws AccessDeniedException;

    public void removeDomain(AuthenticatedUser user, long id, String domainName)
            throws AccessDeniedException, BulkTransferValidationException;

    public void closeTransferRequest(AuthenticatedUser user, long id)
            throws AccessDeniedException, BulkTransferValidationException;

    public void forceCloseTransferRequest(AuthenticatedUser user, long id) throws AccessDeniedException;

    public void transferAll(AuthenticatedUser user, long id)
            throws AccessDeniedException, BulkTransferValidationException, BulkTransferException,
            DefaultsNotFoundException, AccountNotFoundException;

    public void transferValid(AuthenticatedUser user, long id)
            throws AccessDeniedException, BulkTransferValidationException, BulkTransferException,
            DefaultsNotFoundException, AccountNotFoundException;
}
