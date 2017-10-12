package pl.nask.crs.commons.email.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.contacts.Contact;

@Test
public class EmailParametersUtilsTest {
    private String ADMIN_1_NIC = "ADM1024-IEDR";
    private String ADMIN_1_NAME = "Admin 1";
    private String ADMIN_1_EMAIL = "admin1@company.com";
    private String ADMIN_1_COMPANY_NAME = "Admin's Company";
    private String ADMIN_1_COUNTRY = "Swaziland";
    private String ADMIN_1_COUNTY = "N/A";

    private String ADMIN_2_NIC = "ADM2048-IEDR";
    private String ADMIN_2_NAME = "Admin 2";
    private String ADMIN_2_EMAIL = "admin2@company.com";
    private String ADMIN_2_COMPANY_NAME = "Second Admin's Company";
    private String ADMIN_2_COUNTRY = "Tobago";
    private String ADMIN_2_COUNTY = "N/A";

    @Test
    public void testAdminRelatedMethods() throws Exception {
        /* Each admin-related name should be recognised as an admin-related name */
        for (ParameterName param : EmailParametersUtils.getAdminRelatedParameterNames()) {
            Assert.assertEquals(EmailParametersUtils.isAdminRelatedParameterName(param.getName()), true, "Parameter "
                    + param + "(" + param.getName() + ")");
        }

        Contact admin1 = new Contact(ADMIN_1_NIC, ADMIN_1_NAME, ADMIN_1_EMAIL, ADMIN_1_COMPANY_NAME, ADMIN_1_COUNTRY,
                ADMIN_1_COUNTY);
        Contact admin2 = new Contact(ADMIN_2_NIC, ADMIN_2_NAME, ADMIN_2_EMAIL, ADMIN_2_COMPANY_NAME, ADMIN_2_COUNTRY,
                ADMIN_2_COUNTY);
        List<Contact> emptyContacts = new ArrayList<>();
        List<Contact> singleContact = Arrays.asList(admin1);
        List<Contact> twoContacts = Arrays.asList(admin1, admin2);

        // Please note that the behaviour below is based on what DomainEmailParameters does,
        // it does not seem to be very consistent and should be revised soon

        Assert.assertEquals(EmailParametersUtils.getAdminRelatedParameterValue(null,
                    ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), "",
                "Null admins' list email should be empty string");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(emptyContacts,
                        ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), "",
                "Empty admins' list email should be empty string");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(singleContact,
                        ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), ADMIN_1_EMAIL,
                "Single admin's email should be equal to " + ADMIN_1_EMAIL);

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(twoContacts,
                        ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), ADMIN_1_EMAIL + "," + ADMIN_2_EMAIL,
                "Two admins' emails should be equal to " + ADMIN_1_EMAIL + "," + ADMIN_2_EMAIL);

        Assert.assertEquals(EmailParametersUtils.getAdminRelatedParameterValue(null,
                ParameterNameEnum.ADMIN_C_NAME.getName(), false), null, "Null admins' list name should be null");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(emptyContacts,
                        ParameterNameEnum.ADMIN_C_NAME.getName(), false), null,
                "Empty admins' list name should be null");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(singleContact,
                        ParameterNameEnum.ADMIN_C_NAME.getName(), false), ADMIN_1_NAME,
                "Single admin's name should be equal to " + ADMIN_1_NAME);

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(twoContacts,
                        ParameterNameEnum.ADMIN_C_NAME.getName(), false), ADMIN_1_NAME,
                "Two admins' name should be equal to first admin's name - " + ADMIN_1_NAME);

        Assert.assertEquals(EmailParametersUtils.getAdminRelatedParameterValue(null,
                ParameterNameEnum.ADMIN_C_NIC.getName(), false), null, "Null admins' list NIC should be null");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(emptyContacts,
                        ParameterNameEnum.ADMIN_C_NIC.getName(), false), null, "Empty admins' list NIC should be null");

        Assert.assertEquals(
                EmailParametersUtils.getAdminRelatedParameterValue(singleContact,
                        ParameterNameEnum.ADMIN_C_NIC.getName(), false), ADMIN_1_NIC,
                "Single admin's NIC should be equal to " + ADMIN_1_NIC);

        Assert.assertEquals(EmailParametersUtils.getAdminRelatedParameterValue(twoContacts,
                ParameterNameEnum.ADMIN_C_NIC.getName(), false), ADMIN_1_NIC,
                "Two admins' NIC should be equal to first admin's NIC - " + ADMIN_1_NIC);
    }
}
