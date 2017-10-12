package pl.nask.crs.nichandle.service;

import java.util.Collections;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

import mockit.Mocked;
import mockit.NonStrictExpectations;

public class CreateDirectAccountTest extends AbstractContextAwareTest {

    @Resource
    private NicHandleService service;
    @Resource
    private NicHandleSearchService searchService;
    @Resource
    private NicHandleIdDAO nicHandleIdDAO;
    @Resource
    private UserSearchService userSearchService;
    @Resource
    private UserService userService;

    @Mocked
    private EmailTemplateSenderImpl sender;

    private NewAccount newAcc;

    private TestOpInfo opInfo = new TestOpInfo("test", "remark");

    @BeforeMethod
    public void createDirectAccount() throws Exception {
        newAcc = service.createDirectAccount("name", "cname", "email@email.xxx", "address", 199, 0,
                Collections.singletonList("11111111"), Collections.singletonList("11111111"), null, opInfo, "Marysia1",
                false, false);
    }

    @AfterMethod
    public void resetNHIdSeq() {
        nicHandleIdDAO.update(194694L);
    }

    @Test
    public void shouldHavePasswordSet() {
        User u = userSearchService.get(newAcc.getNicHandleId());
        AssertJUnit.assertNotNull("password", u.getPassword());
        AssertJUnit.assertNotNull("salt", u.getSalt());
    }

    @Test
    public void shouldHaveDirectsPermissions() {
        User u = userSearchService.get(newAcc.getNicHandleId());
        AssertJUnit.assertTrue("Belongs to the Direct group", u.hasGroup(Level.Direct));
    }

    @Test
    public void shouldBeTheGuestAccount() throws Exception {
        NicHandle nh = searchService.getNicHandle(newAcc.getNicHandleId());
        AssertJUnit.assertEquals("AccountNo", 1L, nh.getAccount().getId());
    }

    @Test
    public void shouldHaveCreatorFromOpInfo() throws Exception {
        Assert.assertNotNull(newAcc.getNicHandleId());
        NicHandle nh = searchService.getNicHandle(newAcc.getNicHandleId());
        AssertJUnit.assertEquals("creatorNh", opInfo.getActorName(), nh.getCreator());
    }

    @Test
    public void shouldReceiveEmail() throws Exception {
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(),
                        withInstanceOf(NicHandleEmailParameters.class));
                times = 1;
            }
        };
        service.createDirectAccount("name", "cname", "email@email.xxx", "address", 199, 0,
                Collections.singletonList("11111111"), Collections.singletonList("11111111"), null, opInfo, "Marysia1",
                false, false);
    }

}
