package pl.nask.crs.nichandle.service;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.CountyNotExistsException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.VatModificationException;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNewNicHandle;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNicHandle;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.createNewNicHandle;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.createNewNicHandleWithVat;

public class NicHandleServiceTest extends AbstractContextAwareTest {

    @Resource
    NicHandleService service;
    @Resource
    NicHandleSearchService searchService;
    @Resource
    HistoricalNicHandleDAO historicalNicHandleDAO;
    @Resource
    UserDAO userDAO;

    @Mocked
    EmailTemplateSenderImpl sender;

    private final TestOpInfo opInfo = new TestOpInfo("APITEST-IEDR", "nicHandleRemark");
    private final TestOpInfo opInfoNullRemark = new TestOpInfo("APITEST-IEDR");
    private final TestOpInfo opInfoEmptyRemark = new TestOpInfo("APITEST-IEDR", "");

    @Test
    public void alterStatusNicHandleTest() throws Exception {
        service.alterStatus("AA11-IEDR", NicHandleStatus.Suspended, opInfo);
        NicHandle nicHandle = searchService.getNicHandle("AA11-IEDR");
        assertEquals(nicHandle.getStatus(), NicHandleStatus.Suspended);
    }

    @Test(expectedExceptions = NicHandleNotFoundException.class)
    public void alterStatusNicHandleNotExistsTest() throws Exception {
        service.alterStatus("NOT-EXISTS-IEDR", NicHandleStatus.Suspended, opInfo);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void alterStatusNicHandleEmptyRemarkTest() throws Exception {
        service.alterStatus("NOT-EXISTS-IEDR", NicHandleStatus.Suspended, opInfoEmptyRemark);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void alterStatusNicHandleNullRemarkTest() throws Exception {
        service.alterStatus("NOT-EXISTS-IEDR", NicHandleStatus.Suspended, opInfoNullRemark);
    }

    @Test(expectedExceptions = NicHandleAssignedToDomainException.class)
    public void alterStatusNicHandleAssignedToDomainTest() throws Exception {
        service.alterStatus("ACB865-IEDR", NicHandleStatus.Deleted, opInfo);
    }

    @Test
    public void confirmNicHandleIsNotAssingedToAnyDomain() throws Exception {
        service.confirmNicHandleIsNotAssignedToAnyDomain("AA11-IEDR");
    }

    @Test(expectedExceptions = NicHandleAssignedToDomainException.class)
    public void confirmNicHandleIsAssingedToAnyDomain() throws Exception {
        service.confirmNicHandleIsNotAssignedToAnyDomain("AAB069-IEDR");
    }

    @Test
    public void createNicHandleTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());

        newNicHandle.setVatCategory("A");
        compareNewNicHandle(nicHandle, newNicHandle);
        compareNicHandle(nicHandleDB, nicHandle);
        User user = userDAO.get(nicHandle.getNicHandleId());
        assertNotNull(user);
    }

    @Test
    public void createNicHandleMinimalTest() throws Exception {
        NewNicHandle newNicHandle = new NewNicHandle("name", null, "email@aaa.xxx", "address", 199, 0, null, null, null,
                null);
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());

        newNicHandle.setVatCategory("B");
        compareNewNicHandle(nicHandle, newNicHandle);
        compareNicHandle(nicHandleDB, nicHandle);
    }

    @Test
    public void createNicHandleAsSuperUser() throws Exception {
        String userName = "APITEST-IEDR";
        String superUserName = "IDL2-IEDR";
        OpInfo opInfo = new TestOpInfo(userName, superUserName, "remark");
        NewNicHandle newNicHandle = createNewNicHandle();
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());

        newNicHandle.setVatCategory("A");
        compareNewNicHandle(nicHandle, newNicHandle);
        compareNicHandle(nicHandleDB, nicHandle);
        assertEquals(userName, nicHandle.getCreator());
    }

    @Test
    public void createWithNotification() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(),
                        withInstanceOf(NicHandleEmailParameters.class));
                times = 1;
            }
        };
        service.createNicHandle(1L, newNicHandle, opInfo, true);
    }

    @Test
    public void createWithoutNotification() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(),
                        withInstanceOf(NicHandleEmailParameters.class));
                times = 0;
            }
        };
        service.createNicHandle(1L, newNicHandle, opInfo, false);
    }

    @Test
    public void createWithVat() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandleWithVat();
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());
        compareNewNicHandle(nicHandle, newNicHandle);
        compareNicHandle(nicHandleDB, nicHandle);
    }

    @Test
    public void vatCategoryIgnoredDuringCreation() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandleWithVat();
        newNicHandle.setVatCategory("S");
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle nicHandleDB = searchService.getNicHandle(nicHandle.getNicHandleId());
        assertEquals(nicHandle.getVatCategory(), "A");
        compareNicHandle(nicHandleDB, nicHandle);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleNoNameTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setName(null);
        service.createNicHandle(101L, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleEmptyNameTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setName("");
        service.createNicHandle(101L, newNicHandle, opInfo, false);
    }

    @Test
    public void createNicHandleNoCompanyNameTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCompanyName(null);
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle dbNicHandle = searchService.getNicHandle(nicHandle.getNicHandleId());
        assertNull(nicHandle.getCompanyName());
        assertEquals(nicHandle.getCompanyName(), dbNicHandle.getCompanyName());
    }

    @Test
    public void createNicHandleEmptyCompanyNameTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCompanyName("");
        NicHandle nicHandle = service.createNicHandle(101L, newNicHandle, opInfo, false);
        NicHandle dbNicHandle = searchService.getNicHandle(nicHandle.getNicHandleId());
        assertNull(nicHandle.getCompanyName());
        assertEquals(nicHandle.getCompanyName(), dbNicHandle.getCompanyName());
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void createNicHandleNoRemarkTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        service.createNicHandle(101L, newNicHandle, opInfoNullRemark, false);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void createNicHandleEmptyRemarkTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        service.createNicHandle(101L, newNicHandle, opInfoEmptyRemark, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNicHandleNoAccountTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        service.createNicHandle(null, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void createNicHandleNoEmailTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setEmail(null);
        service.createNicHandle(101L, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void createWithNonExistentAccountRemark() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        service.createNicHandle(9999L, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void createWithInactiveAccount() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        service.createNicHandle(100L, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = CountyNotExistsException.class)
    public void createWithCountryCountyMismatch() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCountryId(14);
        newNicHandle.setCountyId(119);
        service.createNicHandle(1L, newNicHandle, opInfo, false);
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void createWithInvalidEmail() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setEmail("a@b@c");
        service.createNicHandle(1L, newNicHandle, opInfo, false);
    }

    @Test
    public void saveTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        NicHandle oldNicHandle = searchService.getNicHandle("AA11-IEDR");
        service.save("AA11-IEDR", 104L, newNicHandle, new TestOpInfo("AA11-IEDR", "remark"), false);
        NicHandle nicHandleDB = searchService.getNicHandle("AA11-IEDR");

        newNicHandle.setVatCategory("A");
        newNicHandle.setVatNo("GB747832695");
        compareNewNicHandle(nicHandleDB, newNicHandle);
        assertEquals("Vat No", oldNicHandle.getVatNo(), nicHandleDB.getVatNo());
    }

    @Test
    public void saveWithVatTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandleWithVat();
        newNicHandle.setVatNo("987654321");
        newNicHandle.setVatCategory("S");
        service.save("AA11-IEDR", 104L, newNicHandle, new TestOpInfo("AA11-IEDR", "remark"), true);
        NicHandle nicHandleDB = searchService.getNicHandle("AA11-IEDR");
        compareNewNicHandle(nicHandleDB, newNicHandle);
    }

    @Test
    public void saveWithEmptyVatTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setVatNo("");
        newNicHandle.setVatCategory("");
        service.save("AA11-IEDR", 104L, newNicHandle, new TestOpInfo("AA11-IEDR", "remark"), true);
        NicHandle nicHandleDB = searchService.getNicHandle("AA11-IEDR");

        newNicHandle.setVatCategory(null);
        newNicHandle.setVatNo(null);
        compareNewNicHandle(nicHandleDB, newNicHandle);
    }

    @Test(expectedExceptions = VatModificationException.class)
    public void saveWithVatNotAllowedTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandleWithVat();
        newNicHandle.setVatCategory("S");
        service.save("AA11-IEDR", 104L, newNicHandle, new TestOpInfo("AA11-IEDR", "remark"), false);
    }

    @Test
    public void modifyAccountTest() throws Exception {
        service.changeNicHandleAccount("AA11-IEDR", 104L, opInfo);
        NicHandle nicHandleDB = searchService.getNicHandle("AA11-IEDR");
        assertEquals(104L, nicHandleDB.getAccount().getId());
    }

    //    TODO: CRS-72
    //    @Test
    //    public void saveVatCategoryRegistrarTest() throws Exception {
    //        String registrarNHId = "AA11-IEDR";
    //        NicHandle nicHandle = searchService.getNicHandle(registrarNHId);
    //        assertEquals("A", nicHandle.getVatCategory());
    //
    //        nicHandle.setVatCategory("S");
    //        service.save(nicHandle, "hRemark", "AA11-IEDR", null);
    //        NicHandle nicHandleDB = searchService.getNicHandle(registrarNHId);
    //        assertEquals("S", nicHandleDB.getVatCategory());
    //
    //        nicHandle.setCountry("Czech Republic"); // VAT category: A
    //        nicHandle.setCounty("N/A");
    //        service.save(nicHandle, "hRemark", "AA11-IEDR", null);
    //        nicHandleDB = searchService.getNicHandle(registrarNHId);
    //        assertEquals("A", nicHandleDB.getVatCategory());
    //    }

    @Test
    public void saveVatCategoryDirectTest() throws Exception {
        String directNHId = "ACB863-IEDR";
        NicHandle nicHandle = searchService.getNicHandle(directNHId);
        assertEquals("A", nicHandle.getVatCategory());

        NewNicHandle newNicHandle = createNewNicHandle(nicHandle);
        newNicHandle.setVatCategory("S");
        service.save(directNHId, 1L, newNicHandle, new TestOpInfo(directNHId, "hRemark"), true);
        NicHandle nicHandleDB = searchService.getNicHandle(directNHId);
        assertEquals("S", nicHandleDB.getVatCategory());

        newNicHandle.setCountryId(65); // Czech Republic
        newNicHandle.setCountyId(0);
        service.save(directNHId, 1L, newNicHandle, new TestOpInfo(directNHId, "hRemark"), true);
        nicHandleDB = searchService.getNicHandle(directNHId);
        assertEquals("B", nicHandleDB.getVatCategory());
    }

    @Test(expectedExceptions = NicHandleNotFoundException.class)
    public void saveNotExistsTest() throws Exception {
        service.save("NOT-EXISTS", 101L, createNewNicHandle(), opInfo, false);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void saveEmptyRemarkTest() throws Exception {
        service.save("AA11-IEDR", 103L, createNewNicHandle(), opInfoEmptyRemark, false);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void saveNullRemarkTest() throws Exception {
        service.save("AA11-IEDR", 103L, createNewNicHandle(), opInfoNullRemark, false);
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void saveAccountDontExistsTest() throws Exception {
        service.save("AA11-IEDR", 12345678L, createNewNicHandle(), opInfo, false);
    }

    @Test(expectedExceptions = NicHandleIsAccountBillingContactException.class)
    public void saveNicHandleIsBillingContactTest() throws Exception {
        service.save("AAA22-IEDR", 103L, createNewNicHandle(), opInfo, false);
    }

    @Test(expectedExceptions = VatModificationException.class)
    public void saveVatCategoryChangedTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCountryId(199);
        newNicHandle.setCountyId(0);
        service.save("AA11-IEDR", 101L, newNicHandle, opInfo, false);
    }

    @Test
    public void saveVatCategoryChangeAllowedTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCountryId(199);
        newNicHandle.setCountyId(0);
        service.save("AA11-IEDR", 101L, newNicHandle, opInfo, true);
    }

    @Test
    public void saveVatCategoryNotMatchCountryUnchangedTest() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCountryId(254);
        newNicHandle.setCountyId(37);
        service.save("AAY760-IEDR", 101L, newNicHandle, opInfo, false);
    }

    @Test
    public void saveShouldCreateHistory() throws Exception {
        String nicHandleId = "AA11-IEDR";
        NewNicHandle newNicHandle = createNewNicHandle();
        service.save(nicHandleId, 101L, newNicHandle, opInfo, false);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    @Test
    public void saveWithNullCompanyName() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCompanyName(null);
        service.save(nicHandleId, 101L, newNicHandle, opInfo, false);
        compareCurrentNhWithHistorical(nicHandleId);
        NicHandle dbNh = searchService.getNicHandle(nicHandleId);
        assertNull(dbNh.getCompanyName());
    }

    @Test
    public void saveWithEmptyCompanyName() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCompanyName("");
        service.save(nicHandleId, 101L, newNicHandle, opInfo, false);
        compareCurrentNhWithHistorical(nicHandleId);
        NicHandle dbNh = searchService.getNicHandle(nicHandleId);
        assertNull(dbNh.getCompanyName());
    }

    @Test
    public void modifyAccountShouldCreateHistory() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        service.changeNicHandleAccount(nicHandleId, 1L, opInfo);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    @Test
    public void createAndCompareResultWithDb() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        NicHandle createdNh = service.createNicHandle(1L, newNicHandle, opInfo, false);
        NicHandle dbNh = searchService.getNicHandle(createdNh.getNicHandleId());
        compareNicHandle(createdNh, dbNh);
    }

    @Test
    public void createWithEmptyCompanyName() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        newNicHandle.setCompanyName("");
        NicHandle createdNh = service.createNicHandle(1L, newNicHandle, opInfo, false);
        NicHandle dbNh = searchService.getNicHandle(createdNh.getNicHandleId());
        assertNull(dbNh.getCompanyName());
        compareNicHandle(createdNh, dbNh);
    }

    @Test
    public void createShouldCreateHistory() throws Exception {
        NewNicHandle newNicHandle = createNewNicHandle();
        NicHandle createdNh = service.createNicHandle(1L, newNicHandle, opInfo, false);
        compareCurrentNhWithHistorical(createdNh.getNicHandleId());
    }

    @Test
    public void alterStatusShouldCreateHistory() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        service.alterStatus(nicHandleId, NicHandleStatus.Suspended, opInfo);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    @Test
    public void triggerExportShouldCreateHistory() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        service.triggerExport(nicHandleId, opInfo);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    @Test
    public void changePasswordShouldCreateHistory() throws Exception {
        String nicHandleId = "AAA22-IEDR";
        service.changePassword("Passw0rd!", "NewPassw0rd!", "NewPassw0rd!", nicHandleId, opInfo);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    @Test
    public void saveNewPasswordShouldCreateHistory() throws Exception {
        String nicHandleId = "AAY760-IEDR";
        service.saveNewPassword("NewPassw0rd!", "NewPassw0rd!", nicHandleId, opInfo, nicHandleId);
        compareCurrentNhWithHistorical(nicHandleId);
    }

    private void compareCurrentNhWithHistorical(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle nicHandle = searchService.getNicHandle(nicHandleId);
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        compareNicHandle(nicHandle, result.getResults().get(0).getObject());
    }

}
