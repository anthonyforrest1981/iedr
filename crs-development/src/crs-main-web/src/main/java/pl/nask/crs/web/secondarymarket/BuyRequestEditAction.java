package pl.nask.crs.web.secondarymarket;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.FailureReason;

import java.util.Arrays;
import java.util.List;

public class BuyRequestEditAction extends BuyRequestAction {

    private String responseText;

    private EntityService entityService;
    private CountryFactory countryFactory;

    public BuyRequestEditAction(SecondaryMarketAppService secondaryMarketAppService, EntityService entityService,
                                CountryFactory countryFactory, DocumentService documentService,
                                ApplicationConfig config) {
        super(secondaryMarketAppService, documentService, config);

        this.entityService = entityService;
        this.countryFactory = countryFactory;
    }

    public String input() throws Exception {
        fetchBuyRequest();
        setResponseText("");
        return INPUT;
    }

    public String save() throws Exception {
        AuthenticatedUser user = getUser();
        if (!validateCurrentBuyRequest())
            return INPUT;

        NewNicHandle newNicHandle = new NewNicHandle(buyRequest.getAdminName(), buyRequest.getAdminCompanyName(),
                buyRequest.getAdminEmail(), buyRequest.getAdminAddress(), buyRequest.getAdminCountry().getId(),
                buyRequest.getAdminCounty().getId(), phonesWrapper.getCurrentList(), faxesWrapper.getCurrentList(),
                null, null);
        secondaryMarketAppService.modifyBuyRequestAsHostmaster(user, buyRequestId, buyRequest.getDomainHolder(),
                buyRequest.getHolderClass().getId(), buyRequest.getHolderCategory().getId(), newNicHandle,
                failures.getDomainNameFR(), failures.getDomainHolderFR(), failures.getHolderCategoryFR(),
                failures.getHolderClassFR(), failures.getAdminNameFR(), failures.getAdminEmailFR(),
                failures.getAdminCompanyNameFR(), failures.getAdminAddressFR(), failures.getAdminCountryFR(),
                failures.getAdminCountyFR(), failures.getPhonesFR(), failures.getFaxesFR(), responseText);
        return SUCCESS;
    }

    private boolean validateCurrentBuyRequest() throws AccessDeniedException, BuyRequestNotFoundException {
        BuyRequest dbRequest = secondaryMarketAppService.getBuyRequest(getUser(), buyRequestId);
        if (!dbRequest.getChangeDate().equals(buyRequest.getChangeDate())) {
            addActionError("BuyRequest was changed in the meantime, please recheck");
            fetchBuyRequest();
            return false;
        }
        return true;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public List<EntityClass> getClasses() {
        return entityService.getClasses();
    }

    public List<FailureReason> getReasonsList() {
        return Arrays.asList(FailureReason.values());
    }

    public List<Country> getCountries() {
        return countryFactory.getCountries();
    }
}
