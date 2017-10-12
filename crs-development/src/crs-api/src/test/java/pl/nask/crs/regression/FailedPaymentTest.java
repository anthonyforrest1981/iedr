package pl.nask.crs.regression;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import mockit.Mocked;
import mockit.NonStrictExpectations;

/**
 * bug #13307
 * https://drotest4.nask.net.pl:3000/issues/13307
 *
 * Email is being triggered even when payment fails on new reg and transfer tickets generated via Console.
 *
 * Test steps:
 * 1. Console > Domains > Register New
 * 2. Fill in form (i selected one year but any period can be seleted)
 * 3. Choose credit card option for payment
 * 4. Enter a Mastercard number but select one of the VISA icons for payment type.
 * 5. The error is displayed to screen correctly: "Error. That Card Number does not correspond to the card type you selected "
 * 6. No ticket is created which is correct
 * 7. However an email is triggered to the client to say that the application has been received:
 *
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class FailedPaymentTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mocked
    EmailTemplateSenderImpl emailTemplateSender;

    @Autowired
    CommonAppService commonAppService;

    @Autowired
    DomainSearchService domainSearchService;

    @Autowired
    DomainService domainService;

    @Autowired
    NicHandleDAO nicHandleDAO;

    @Autowired
    HistoricalNicHandleDAO historicalNicHandleDAO;

    @Mocked
    pl.nask.crs.api.payment.PaymentSenderTestImpl paymentSender;

    private String domainForTransfer = "thedomain-totransfer.ie";
    private String losingRegistrar = "APIT2-IEDR"; // account 668
    private String gainingRegistrar = "APITEST-IEDR"; // 666

    @Test(expectedExceptions = CardPaymentException.class)
    public void noEmailShouldBeSentWhenWrongCardDataAreProvidedWhenTransferingDomain() throws Exception {
        // given
        String losingName = "losing";
        String gainingName = "gaining";
        setName(gainingRegistrar, gainingName);
        setName(losingRegistrar, losingName);

        TicketRequest req = prepareTransferRequest();
        Domain beforeTransfer = getDomain();

        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());

        // when
        createExpectations();
        performDomainTransfer(req, incorrectCreditCard());

        // then
        // the expectations are met
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void noEmailShouldBeSentWhenWrongCardDataAreProvidedWhenRegisteringDomain() throws Exception {
        // given
        String losingName = "losing";
        String gainingName = "gaining";
        setName(gainingRegistrar, gainingName);
        setName(losingRegistrar, losingName);

        TicketRequest req = prepareRegRequest();
        // when
        createExpectations();
        commonAppService.registerDomain(getAuthenticatedUser(gainingRegistrar), req, incorrectCreditCard());

        // then
        // the expectations are met
    }

    private TicketRequest prepareRegRequest() {
        final Domain d = getDomain();
        RegistrationRequestVO req = new RegistrationRequestVO();
        req.setAdminContactNicHandles(Arrays.asList(d.getFirstAdminContactNic(), d.getSecondAdminContactNic()));
        req.setTechContactNicHandle(d.getTechContactNic());
        req.setDomainHolder(d.getHolder());
        req.setDomainOwnerTypeId(1L);
        req.setDomainName("newdomain.ie");
        req.resetToNameservers(d.getNameservers());
        req.setPeriod(1);
        req.setPeriodType(PeriodType.Y);
        return req;
    }

    private CreditCard incorrectCreditCard() {
        return new CreditCard("5425230000000009", "2021/01", "VISA", "John Doe", "123", 1);
    }

    private final static String realexErrorResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
            + "<response timestamp=\"20120319115651\">\n"
            + "<merchantid>iedr</merchantid>\n"
            + "<account>internet</account>\n"
            + "<orderid>20120319125630-103067</orderid>\n"
            + "<authcode>115651</authcode>\n"
            + "<result>55</result>\n"
            + // some error code here
            "<cvnresult>U</cvnresult>\n" + "<avspostcoderesponse>U</avspostcoderesponse>\n"
            + "<avsaddressresponse>U</avsaddressresponse>\n" + "<batchid>-1</batchid>\n"
            + "<message>[ test system ] AUTH CODE 115651</message>\n" + "<pasref>133215821111512</pasref>\n"
            + "<timetaken>0</timetaken>\n" + "<authtimetaken>0</authtimetaken>\n" + "<cardissuer>\n"
            + "<bank>AIB BANK</bank>\n" + "<country>IRELAND</country>\n" + "<countrycode>IE</countrycode>\n"
            + "<region>EUR</region>\n" + "</cardissuer>\n" + "<md5hash>84cb753995cded64ff90a9a20bf2866f</md5hash>\n"
            + "</response>";

    private void createExpectations()
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        new NonStrictExpectations() {
            {
                paymentSender.send(anyString);
                result = realexErrorResponse;
            }
        };
        new NonStrictExpectations() {
            {
                emailTemplateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                maxTimes = 0;
            }
        };
    }

    private void setName(String nh, String newName) {
        NicHandle nicHandle = nicHandleDAO.get(nh);
        nicHandle.setName(newName);
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR"));
    }

    private Map<ParameterNameEnum, String> populatedValues(String losingName, String gainingName) {
        Map<ParameterNameEnum, String> map = new HashMap<ParameterNameEnum, String>();
        map.put(ParameterNameEnum.LOSING_BILL_C_NAME, losingName);
        map.put(ParameterNameEnum.GAINING_BILL_C_NAME, gainingName);
        return map;
    }

    private TicketRequest prepareTransferRequest() throws Exception {
        final Domain d = getDomain();
        AuthenticatedUser user = getAuthenticatedUser(losingRegistrar);
        String authCode = domainService.getOrCreateAuthCode(d.getName(), new OpInfo(user)).getAuthCode();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContactNicHandles(Arrays.asList(d.getFirstAdminContactNic(), d.getSecondAdminContactNic()));
        req.setTechContactNicHandle(d.getTechContactNic());
        req.setDomainHolder(d.getHolder());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(1);
        req.setPeriodType(PeriodType.Y);
        return req;
    }

    private AuthenticatedUser getAuthenticatedUser(final String username) {
        return new AuthenticatedUser() {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getSuperUserName() {
                return null;
            }

            @Override
            public String getAuthenticationToken() {
                return "";
            }
        };
    }

    private Domain getDomain() {
        try {
            return domainSearchService.getDomain(domainForTransfer);
        } catch (DomainNotFoundException e) {
            throw new IllegalStateException("Domain not found: " + domainForTransfer, e);
        }
    }

    private void performDomainTransfer(TicketRequest req, CreditCard creditCard) throws Exception {
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, creditCard);
    }

}
