package pl.nask.crs.nichandle.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.nichandle.service.impl.helper.PasswordHelper;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;

public class NicHandleCreationTest extends AbstractContextAwareTest {
    @Resource
    NicHandleService service;
    @Resource
    NicHandleSearchService searchService;
    @Resource
    NicHandleIdDAO nicHandleIdDAO;

    @Mocked
    PasswordHelper passwordHelper;

    @Mocked
    EmailTemplateSenderImpl emailSender;

    @Test
    public void passwordShouldNotBeSentInNotificationEmail() throws Exception {
        List<String> phones = Arrays.asList("22", "33");
        List<String> faxes = Arrays.asList("333");
        final String newPassword = "password";
        new Expectations() {
            {
                passwordHelper.generateNewPassword(anyInt);
                result = newPassword;
            }
        };
        new Expectations() {
            {
                emailSender.sendEmail(anyInt, withInstanceOf(NicHandleEmailParameters.class));
                result = new Delegate() {
                    public void validate(int templateId, NicHandleEmailParameters params) {
                        AssertJUnit.assertFalse("Password should not be present in email parameters", newPassword
                                .equals(params.getParameterValue(ParameterNameEnum.PASSWORD_TOKEN.getName(), false)));
                    }
                };
            }
        };
        service.createNicHandle(101L, new NewNicHandle("name", "companyName", "email@aaa.xxx", "address", 199, 0,
                phones, faxes, "222333444", null), new TestOpInfo("TEST-IEDR", "nicHandleRemark"), true);
        nicHandleIdDAO.update(194694L);
    }
}
