package pl.nask.crs.nichandle.service.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.accounts.services.impl.AccountUpdateExporter;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.country.*;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.nichandle.email.TacNicHandleDetailsEmailParams;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.nichandle.service.NicHandleIdGenerator;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.nichandle.service.impl.helper.NicHandleValidator;
import pl.nask.crs.nichandle.service.impl.helper.PasswordHelper;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

public class NicHandleServiceImpl implements NicHandleService {

    private final static String RESET_PASSWORD_REMARK = "Change password";
    private final static NicHandleStatus DEFAULT_STATUS = NicHandleStatus.Active;
    private final static boolean DEFAULT_BILLC_IND = false;
    private final static Logger logger = Logger.getLogger(NicHandleServiceImpl.class);

    private NicHandleDAO nicHandleDAO;
    private HistoricalNicHandleDAO historicalNicHandleDAO;
    private NicHandleIdGenerator nicHandleIdGenerator;
    private UserService userService;
    private UserSearchService userSearchService;
    private ContactSearchService contactSearchService;
    private CountryFactory countryFactory;
    private final AccountUpdateExporter exporter;
    private AccountSearchService accountSearchService;
    private ServicesRegistry servicesRegistry;
    private DomainSearchService domainSearchService;
    private TicketSearchService ticketSearchService;

    public NicHandleServiceImpl(NicHandleDAO nicHandleDAO, HistoricalNicHandleDAO historicalNicHandleDAO,
            NicHandleIdGenerator nicHandleIdGenerator, UserService userService, UserSearchService userSearchService,
            ContactSearchService contactSearchService, CountryFactory countryFactory, AccountUpdateExporter exporter,
            AccountSearchService accountSearchService, DomainSearchService domainSearchService,
            TicketSearchService ticketSearchService) {
        Validator.assertNotNull(nicHandleDAO, "nic handle dao");
        Validator.assertNotNull(historicalNicHandleDAO, "historical nic handle dao");
        Validator.assertNotNull(nicHandleIdGenerator, "nic handle id generator");
        Validator.assertNotNull(userService, "user service");
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(contactSearchService, "contact search service");
        Validator.assertNotNull(countryFactory, "countryFactory");
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        this.nicHandleDAO = nicHandleDAO;
        this.historicalNicHandleDAO = historicalNicHandleDAO;
        this.nicHandleIdGenerator = nicHandleIdGenerator;
        this.userService = userService;
        this.userSearchService = userSearchService;
        this.contactSearchService = contactSearchService;
        this.countryFactory = countryFactory;
        this.exporter = exporter;
        this.accountSearchService = accountSearchService;
        this.domainSearchService = domainSearchService;
        this.ticketSearchService = ticketSearchService;
    }

    @Override
    public void save(String nicHandleId, Long accountId, NewNicHandle newNicHandle, OpInfo opInfo,
            boolean allowVatChange)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException,
            VatModificationException {
        Validator.assertNotNull(newNicHandle, "nic handle");
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        int oldCountryId = nicHandle.getCountry().getId();
        copyNonVatValues(nicHandle, newNicHandle);
        NicHandleValidator.validateNicHandle(nicHandle.getName(), nicHandle.getEmail(), nicHandle.getAddress(),
                nicHandle.getCountry(), nicHandle.getCounty(), accountId, countryFactory, accountSearchService);
        boolean vatChanged = applyVatChanges(nicHandle, newNicHandle.getVatNo(), newNicHandle.getVatCategory(),
                oldCountryId, allowVatChange);
        performUpdate(nicHandle, accountId, opInfo);
        if (vatChanged) {
            sendNicHandleEmail(EmailTemplateNamesEnum.VAT_CHANGE, nicHandle, null, opInfo.getUserName());
        }
    }

    @Override
    public void changeNicHandleAccount(String nicHandleId, Long accountId, OpInfo opInfo)
            throws NicHandleNotFoundException, AccountNotActiveException, AccountNotFoundException,
            NicHandleIsAccountBillingContactException, ExportException, EmptyRemarkException, InvalidEmailException,
            InvalidCountyException {
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        if (nicHandle.getAccount().getId() != accountId) {
            performUpdate(nicHandle, accountId, opInfo);
        }
    }

    private void performUpdate(NicHandle nicHandle, Long accountId, OpInfo opInfo)
            throws NicHandleNotFoundException, AccountNotActiveException, AccountNotFoundException,
            NicHandleIsAccountBillingContactException, ExportException {
        lock(nicHandle.getNicHandleId());
        accountSearchService.confirmAccountActive(accountId);
        confirmAccountCanBeChanged(nicHandle.getNicHandleId(), nicHandle.getAccount().getId(), accountId);
        Account account = accountSearchService.getAccount(accountId);
        nicHandle.setAccount(account);
        updateNicHandleAndHistory(nicHandle, opInfo);
        exportChange(nicHandle, false, OpInfo.withModifiedRemark(opInfo, "NicHandle was exported"));
        sendUpdateNotification(opInfo.getUserName(), nicHandle);
    }

    private void copyNonVatValues(NicHandle nicHandle, NewNicHandle newNicHandle)
            throws InvalidCountryException, InvalidCountyException {
        nicHandle.setName(newNicHandle.getName());
        nicHandle.setCompanyName(newNicHandle.getCompanyName());
        nicHandle.setEmail(newNicHandle.getEmail());
        nicHandle.setAddress(newNicHandle.getAddress());
        nicHandle.setCountry(countryFactory.getCountry(newNicHandle.getCountryId()));
        nicHandle.setCounty(countryFactory.getCounty(newNicHandle.getCountyId()));
        nicHandle.setPhones(newNicHandle.getPhones());
        nicHandle.setFaxes(newNicHandle.getFaxes());
    }

    private void sendUpdateNotification(String username, NicHandle nicHandle) {
        NicHandle creator = nicHandleDAO.get(username);
        NicHandle billingNh = getBillingNicHandleFor(nicHandle);
        EmailParameters templateParameters =
                new TacNicHandleDetailsEmailParams(creator, nicHandle, billingNh, username);
        User u = userSearchService.get(nicHandle.getNicHandleId());

        EmailTemplateNamesEnum template = null;
        if (u != null) {
            if (u.hasDefaultAccessOnly()) {
                if (servicesRegistry.getNicHandleSearchService().isNicHandleDirect(nicHandle)) {
                    template = EmailTemplateNamesEnum.TAC_NIC_HANDLE_DETAILS_AMENDED_DIRECT;
                } else {
                    template = EmailTemplateNamesEnum.TAC_NIC_HANDLE_DETAILS_AMENDED_REGISTRAR;
                }
            } else {
                if (u.hasGroup(Level.Direct) || u.hasGroup(Level.Registrar) || u.hasGroup(Level.SuperRegistrar)) {
                    template = EmailTemplateNamesEnum.ACCOUNT_DATA_UPDATE;
                }
            }
        }

        if (template != null) {
            try {
                servicesRegistry.getEmailTemplateSender().sendEmail(template.getId(), templateParameters);
            } catch (Exception e) {
                logger.error("Problems when sending an email with id= " + template.getId(), e);
            }
        }
    }

    private NicHandle getBillingNicHandleFor(NicHandle nh) {
        if (servicesRegistry.getNicHandleSearchService().isNicHandleDirect(nh)) {
            NicHandle ret = nicHandleDAO.getDirectNhForContact(nh.getNicHandleId());
            if (ret == null) {
                logger.warn("Direct registrar not found for " + nh.getNicHandleId());
            }
            return ret;
        } else {
            return nicHandleDAO.get(nh.getAccount().getBillingContact().getNicHandle());
        }
    }

    private boolean applyVatChanges(NicHandle nicHandle, String newVatNo, String newVatCategory, int oldCountryId,
            boolean allowVatChange) throws VatModificationException {
        boolean vatNoChanged = applyVatNoChange(nicHandle, newVatNo, allowVatChange);
        String oldVatCategory = nicHandle.getVatCategory();
        boolean vatCategoryChanged = applyVatCategoryChange(nicHandle, newVatCategory, allowVatChange);
        boolean vatCategoryChangeImpliedByCountry = applyVatCategoryChangeImpliedByCountry(nicHandle, oldVatCategory,
                oldCountryId, allowVatChange);
        return (vatNoChanged || vatCategoryChanged || vatCategoryChangeImpliedByCountry);
    }

    private boolean applyVatCategoryChangeImpliedByCountry(NicHandle nicHandle, String oldVatCategory,
            int oldCountryId, boolean allowVatChange)
            throws VatModificationException {
        if (nicHandle.getCountry().getId() == oldCountryId) {
            return false;
        }
        String newVatCategoryByCountry = countryFactory.getCountryVatCategory(nicHandle.getCountry().getId());
        boolean vatCategoryChangeImpliedByCountry = !Validator.isEqual(oldVatCategory, newVatCategoryByCountry);
        if (vatCategoryChangeImpliedByCountry) {
            if (!allowVatChange) {
                throw new VatModificationException(
                        "Country modification is not allowed since it changes vat category.");
            }
            boolean vatCategoryExplicitChange = !Validator.isEqual(oldVatCategory, nicHandle.getVatCategory());
            if (!vatCategoryExplicitChange) {
                nicHandle.setVatCategory(newVatCategoryByCountry);
                return true;
            }
        }
        return false;
    }

    /**
     * @param initialExport - the value to be verified as non-equal to nicHandle.isExported(). There are two cases:
     *     1. If a Mod file for the nicHandle has been already exported, a new export is performed only if the value is false
     *        (ie. the method is called on Nic Handle modification)
     *     2. If a Mod file for the nicHandle has not been exported yet, a new export is performed only if the value is true
     *        (ie. the method is called on registration of a new domain or on transfer)
     */
    private void exportChange(NicHandle nicHandle, boolean initialExport, OpInfo opInfo) throws ExportException {
        if (initialExport && nicHandle.isExported()) {
            // Already exported - no new export needed
            return;
        }
        if (!initialExport && !nicHandle.isExported()) {
            logger.info("NicHandle export skipped (modifying not exported Nic Handle).");
            return;
        }
        long accountId = nicHandle.getAccount().getId();
        logger.info("NicHandle export started.");
        exporter.exportAccount(accountId, nicHandle.getName(), nicHandle.getVatNo(), nicHandle.getAddress(),
                nicHandle.getNicHandleId(), nicHandle.getCountry().getName(), nicHandle.getCounty().getName(),
                nicHandle.getChangeDate(), nicHandle.getPhonesAsString(), nicHandle.getFaxesAsString(),
                nicHandle.getEmail(), nicHandle.getVatCategory());
        markAsExported(nicHandle, opInfo);
    }

    @Override
    public void triggerExport(String nicHandleId, OpInfo opInfo)
            throws ExportException, NicHandleNotFoundException {
        NicHandle nh = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        exportChange(nh, true, opInfo);
    }

    private void markAsExported(NicHandle nicHandle, OpInfo opInfo) {
        if (nicHandle.isExported()) {
            return;
        }
        nicHandle.setExported(true);
        updateNicHandleAndHistory(nicHandle, opInfo);
    }

    @Override
    public void alterStatus(String nicHandleId, NicHandleStatus status, OpInfo opInfo)
            throws NicHandleNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException,
            NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException {
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        Validator.assertNotNull(status, "nic handle status");
        if (status == NicHandleStatus.Deleted) {
            confirmNicHandleNotAccountBillingContact(nicHandleId);
            confirmNicHandleIsNotAssignedToAnyDomain(nicHandleId);
            confirmNicHandleIsNotTicketContact(nicHandleId);
        }
        NicHandle nicHandle = lock(nicHandleId);
        if (nicHandle.setStatus(status)) {
            updateNicHandleAndHistory(nicHandle, opInfo);
        }
    }

    @Override
    public void saveNewPassword(String password1, String password2, String nicHandleId, OpInfo opInfo,
            String loggedUserName)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(opInfo, "opInfo");
        PasswordHelper.validatePassword(password1, password2);
        NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        changePassword(nicHandle, password1, opInfo);
        sendNicHandleEmail(EmailTemplateNamesEnum.CHANGE_PASSWORD, nicHandle, null, loggedUserName);
    }

    private void changePassword(NicHandle nicHandle, String password, OpInfo opInfo)
            throws PasswordAlreadyExistsException {
        // non-intuitive order of updates is done so that AccessHist will point at correct NicHandleHist row.
        updateNicHandleAndHistory(nicHandle, OpInfo.withModifiedRemark(opInfo, RESET_PASSWORD_REMARK));
        userService.changePassword(nicHandle.getNicHandleId(), password, opInfo);
    }

    @Override
    public void changePassword(String oldPassword, String password1, String password2, String nicHandleId,
            OpInfo opInfo)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(opInfo, "opInfo");
        PasswordHelper.validatePassword(password1, password2);
        NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        updateNicHandleAndHistory(nicHandle, OpInfo.withModifiedRemark(opInfo, RESET_PASSWORD_REMARK));
        userService.changePassword(nicHandleId, oldPassword, password1, opInfo);
        sendNicHandleEmail(EmailTemplateNamesEnum.CHANGE_PASSWORD, nicHandle, null, opInfo.getUserName());
    }

    @Override
    public void resetPassword(String nicHandleId, OpInfo opInfo, String ipAddress, String loggedUserName)
            throws NicHandleNotFoundException, NicHandleEmailException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(opInfo, "opInfo");
        String token = PasswordHelper.generateNewPassword(16);
        userService.resetPassword(nicHandleId, token, ipAddress, opInfo);
        NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(nicHandleId);
        sendNicHandleEmail(EmailTemplateNamesEnum.RESET_PASSWORD, nicHandle, token, loggedUserName);
    }

    @Override
    public NicHandle createNicHandle(Long accountNumber, NewNicHandle newNicHandle, OpInfo opInfo,
            boolean sendNotificationEmail)
            throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            NicHandleEmailException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, InvalidEmailException {
        return createNicHandle(newNicHandle.getName(), newNicHandle.getCompanyName(), newNicHandle.getEmail(),
                newNicHandle.getAddress(), newNicHandle.getCountryId(), newNicHandle.getCountyId(), accountNumber,
                newNicHandle.getPhones(), newNicHandle.getFaxes(), newNicHandle.getVatNo(), opInfo,
                sendNotificationEmail, false);
    }

    private NicHandle createNicHandle(String name, String companyName, String email, String address, int countryId,
            int countyId, Long accountNumber, /*>>>@Nullable*/ List<String> phones, /*>>>@Nullable*/ List<String> faxes,
            /*>>>@Nullable*/ String vatNo, OpInfo opInfo, boolean sendNotificationEmail,
            boolean isDirectAccountCreation)
            throws AccountNotFoundException, AccountNotActiveException, EmptyRemarkException, NicHandleEmailException,
            PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, InvalidEmailException {
        validateRemark(opInfo.getRemark());
        Country country = countryFactory.getCountry(countryId);
        County county = countryFactory.getCounty(countyId);
        NicHandleValidator.validateNicHandle(name, email, address, country, county, accountNumber, countryFactory,
                accountSearchService);
        String nicHandleId = nicHandleIdGenerator.generateNicHandleId();
        Date todayDate = new Date();

        NicHandle nicHandle = new NicHandle(nicHandleId, name, new Account(accountNumber), companyName, address, phones,
                faxes, country, county, email, DEFAULT_STATUS, todayDate, todayDate, todayDate, DEFAULT_BILLC_IND,
                opInfo.getRemark(), opInfo.getUserName(), vatNo, countryFactory.getCountryVatCategory(countryId),
                false);

        nicHandle.addActorNameToRemark(opInfo.getActorNameForRemark());
        nicHandleDAO.create(nicHandle);
        // Create entry in history and use its changeId to update the nic handle.
        updateNicHandleAndHistory(nicHandle, opInfo);

        NicHandle dbNicHandle = nicHandleDAO.get(nicHandleId);
        if (!isDirectAccountCreation) {
            String newPassword = PasswordHelper.generateNewPassword(16);
            changePassword(dbNicHandle, newPassword, opInfo);
        }
        if (sendNotificationEmail) {
            sendNicHandleCreationEmail(dbNicHandle, isDirectAccountCreation, opInfo.getUserName());
        }
        return dbNicHandle;
    }

    @Override
    public void confirmNicHandleIsNotAssignedToAnyDomain(String nicHandleId) throws NicHandleAssignedToDomainException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle(nicHandleId);
        if (domainSearchService.exists(criteria))
            throw new NicHandleAssignedToDomainException(nicHandleId);
    }

    private void confirmNicHandleNotAccountBillingContact(String nicHandleId)
            throws NicHandleIsAccountBillingContactException {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setNicHandle(nicHandleId);
        if (accountSearchService.exists(criteria))
            throw new NicHandleIsAccountBillingContactException(nicHandleId);

    }

    private void confirmNicHandleIsNotTicketContact(String nicHandleId) throws NicHandleIsTicketContactException {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAnyContactNH(nicHandleId);
        if (ticketSearchService.exists(criteria))
            throw new NicHandleIsTicketContactException(nicHandleId);
    }

    private NicHandle lock(String nicHandleId) throws NicHandleNotFoundException {
        if (nicHandleDAO.lock(nicHandleId)) {
            return nicHandleDAO.get(nicHandleId);
        } else {
            throw new NicHandleNotFoundException(nicHandleId);
        }
    }

    private void updateNicHandleAndHistory(NicHandle nicHandle, OpInfo opInfo) {
        nicHandle.updateChangeDate();
        nicHandle.setNicHandleRemark(opInfo.getRemark());
        nicHandle.addActorNameToRemark(opInfo.getActorNameForRemark());
        long changeId = historicalNicHandleDAO.create(nicHandle, nicHandle.getChangeDate(), opInfo.getActorName());
        nicHandleDAO.updateUsingHistory(changeId);
    }

    /**
     * Function checks if the account number of a nic handle can be changed.
     * Account number cannot be changed if:
     * - the current account is a direct account and the new account number != current and the nic handle is in Contact table with type = "B" for any domain.
     * or
     * - the current account number > 99 and the new account number != current account number and the nic handle is listed in Account table as Biling_NH for the current account number.
     *
     * @throws pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException exception thrown when the change cannot be made
     */
    private void confirmAccountCanBeChanged(String nicHandleId, long oldAccountId, long newAccountNumber)
            throws NicHandleIsAccountBillingContactException {
        if (servicesRegistry.getNicHandleSearchService().isAccountDirect(oldAccountId) &&
                !servicesRegistry.getNicHandleSearchService().isAccountDirect(newAccountNumber)) {
            ContactSearchCriteria criteria = new ContactSearchCriteria();
            criteria.setNicHandle(nicHandleId);
            criteria.setType(ContactType.BILLING);
            if (contactSearchService.contactExists(criteria))
                throw new NicHandleIsAccountBillingContactException(nicHandleId, oldAccountId);
        }
        if (oldAccountId > 99 && newAccountNumber != oldAccountId) {
            AccountSearchCriteria criteria = new AccountSearchCriteria();
            criteria.setId(oldAccountId);
            criteria.setNicHandle(nicHandleId);
            if (accountSearchService.exists(criteria))
                throw new NicHandleIsAccountBillingContactException(nicHandleId, oldAccountId);
        }
    }

    private boolean applyVatNoChange(NicHandle nicHandle, String newVatNo, boolean allowVatChange)
            throws VatModificationException {
        if (!allowVatChange) {
            if (!Validator.isEmpty(newVatNo)) {
                throw new VatModificationException("Vat number changed");
            }
        } else {
            String vatNoValue = Validator.isEmpty(newVatNo) ? null : newVatNo;
            boolean vatNoChanged = !Validator.isEqual(nicHandle.getVatNo(), vatNoValue);
            if (vatNoChanged) {
                nicHandle.setVatNo(vatNoValue);
                return true;
            }
        }
        return false;
    }

    private boolean applyVatCategoryChange(NicHandle nicHandle, String newVatCategory, boolean allowVatChange)
            throws VatModificationException {
        if (!allowVatChange) {
            if (!Validator.isEmpty(newVatCategory)) {
                throw new VatModificationException("Vat category changed");
            }
        } else {
            String vatCategoryValue = Validator.isEmpty(newVatCategory) ? null : newVatCategory;
            boolean vatCategoryChanged = (!Validator.isEqual(nicHandle.getVatCategory(), vatCategoryValue));
            if (vatCategoryChanged) {
                nicHandle.setVatCategory(vatCategoryValue);
                return true;
            }
        }
        return false;
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    @Override
    public void removeDeletedNichandles() {
        nicHandleDAO.deleteMarkedNichandles();
    }

    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
        userService.addUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
        userService.removeUserPermission(nicHandleId, permissionName);
    }

    @Override
    public NewAccount createDirectAccount(String name, String companyName, String email, String address,
            int countryId, int countyId, List<String> phones, List<String> faxes, String vatNo, OpInfo opInfo,
            String password, boolean useTfa, boolean forcePasswordChange)
            throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            NicHandleEmailException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, InvalidEmailException {
        Long accountNumber = servicesRegistry.getApplicationConfig().getGuestAccountId();
        NicHandle nh = createNicHandle(name, companyName, email, address, countryId, countyId, accountNumber, phones,
                faxes, vatNo, opInfo, true, true);
        userService.changePassword(nh.getNicHandleId(), password, forcePasswordChange, opInfo);
        userService.addUserToGroup(nh.getNicHandleId(), Level.Direct, opInfo);
        String secret = userService.changeTfa(nh.getNicHandleId(), useTfa, opInfo);
        return new NewAccount(nh.getNicHandleId(), secret);
    }

    private void sendNicHandleCreationEmail(NicHandle nicHandle, boolean isDirectAccountCreation, String loggedUserName)
            throws NicHandleEmailException {
        if (isDirectAccountCreation) {
            NicHandle billingNh = null;
            sendNicHandleEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE, nicHandle, billingNh, null,
                    loggedUserName);
        } else {
            sendNicHandleEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE, nicHandle, null, loggedUserName);
        }
    }

    private void sendNicHandleEmail(EmailTemplateNamesEnum emailTemplate, NicHandle nicHandle,
            String passwordResetToken, String loggedUserName)
            throws NicHandleEmailException {
        NicHandle billingNh = getBillingNicHandleFor(nicHandle);
        sendNicHandleEmail(emailTemplate, nicHandle, billingNh, passwordResetToken, loggedUserName);
    }

    private void sendNicHandleEmail(EmailTemplateNamesEnum emailTemplate, NicHandle nicHandle, NicHandle billingNh,
            String passwordResetToken, String loggedUserName)
            throws NicHandleEmailException {
        NicHandleEmailParameters params = new NicHandleEmailParameters(nicHandle, passwordResetToken,
                loggedUserName, (billingNh == null) ? null : billingNh.getNicHandleId());
        try {
            servicesRegistry.getEmailTemplateSender().sendEmail(emailTemplate.getId(), params);
        } catch (Exception e) {
            logger.error("Problem with sending nic handle notification", e);
            throw new NicHandleEmailException(nicHandle.getNicHandleId(), e);
        }
    }

    public ServicesRegistry getServicesRegistry() {
        return servicesRegistry;
    }

    public void setServicesRegistry(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

}
