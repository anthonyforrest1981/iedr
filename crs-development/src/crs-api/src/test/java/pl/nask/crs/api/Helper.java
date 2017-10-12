package pl.nask.crs.api;

import java.util.Arrays;

import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class Helper {

    public static RegistrationRequestVO createBasicCreateRequest(String domainName, String contact) {
        RegistrationRequestVO request = new RegistrationRequestVO();
        request.setDomainName(domainName);
        request.setDomainHolder("John Doe");
        request.setDomainOwnerTypeId(1L);
        request.setAdminContactNicHandles(Arrays.asList(contact, null));
        request.setTechContactNicHandle(contact);
        request.setNameservers(Arrays.asList(new Nameserver("ns1." + domainName, "1.1.1.1", null), new Nameserver(
                "ns1.example-nameserver.ie", null, null)));
        request.setRequestersRemark("r remark");
        request.setPeriod(1);
        request.setPeriodType(TicketRequest.PeriodType.Y);
        return request;
    }

    public static RegistrationRequestVO createCharityRegistrationRequest(String domainName, String contact) {
        RegistrationRequestVO req = createBasicCreateRequest(domainName, contact);
        req.setCharityCode("charityCode");
        return req;
    }

    public static CreditCardVO createBasicCreditCard() {
        CreditCardVO creditCard = new CreditCardVO();
        creditCard.setCardNumber("4263971921001307");
        creditCard.setCardType("VISA");
        creditCard.setCardHolderName("John Doe");
        creditCard.setCardExpDate("0115");
        creditCard.setCvnNumber("123");
        creditCard.setCvnPresenceIndicator(1);
        return creditCard;
    }

    public static TransferRequestVO createBasicTransferRequest(String domainName, String techContact, boolean charity,
            int periodInYears, String authCode) {
        TransferRequestVO request = new TransferRequestVO();
        request.setDomainName(domainName);
        request.setTechContactNicHandle(techContact);
        request.setNameservers(Arrays.asList(new Nameserver("ns1." + domainName, "1.1.1.1", null), new Nameserver(
                "ns1.example-nameserver.ie", null, null)));
        request.setRequestersRemark("r remark");
        request.setAuthCode(authCode);
        request.setCharity(charity);
        if (!charity) {
            request.setPeriod(periodInYears);
            request.setPeriodType(TicketRequest.PeriodType.Y);
        }
        return request;
    }
}
