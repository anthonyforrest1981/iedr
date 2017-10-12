package pl.nask.crs.accounts.services;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;

public interface AccountService {

    void alterStatus(Long id, AccountStatus status, OpInfo opInfo)
            throws AccountNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException;

    void save(Account account, OpInfo opInfo)
            throws AccountNotFoundException, EmptyRemarkException, ContactNotFoundException,
            NicHandleNotFoundException, ContactCannotChangeException, ExportException;

    void createAccount(CreateAccountContener createAccountContener, OpInfo opInfo)
            throws EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException,
            NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException,
            NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, ExportException,
            InvalidEmailException, InvalidCountyException;

}
