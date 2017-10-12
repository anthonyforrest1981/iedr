package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.EditNicHandlePage;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.EmailAddressUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static org.junit.Assert.assertEquals;

public class UC051 extends SeleniumTest {

    boolean accountModified = false;
    int lastAccountId;
    OutputFiles outputFiles;

    public UC051(Browser browser) {
        super(browser);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        lastAccountId = db().getLastAccountId();
        outputFiles = new OutputFiles(ssh().crsweb);
    }

    @Override
    public void tearDown() throws Exception {
        // Remove created XML file.
        try {
            if (accountModified) {
                outputFiles.clearAccountXmls();
            }
        } finally {
            super.tearDown();
        }
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc051_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc051_data.sql";
    }

    @Test
    public void test_uc051_sc03() throws SQLException, JSchException, IOException {
        User user = new User("UC051AA-IEDR", "Password!", false, "uc051_aa@iedr.ie");
        List<InputField> inputFields = new ArrayList<InputField>();
        inputFields.add(new InputField(By.name("wrapper.address"), "Modified address", "Address"));
        inputFields.add(new InputField(By.cssSelector("#cns_Phone0div input[name='wrapper.phonesWrapper.phone']"),
                "987654321", "Phone"));
        inputFields.add(new InputField(By.name("hostmastersRemark"), "Remark", "Remark"));
        List<SelectField> selectFields = new ArrayList<SelectField>();
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        test_modify_customer_account("UC051AA-IEDR", inputFields, selectFields, true);
    }

    @Test
    public void test_uc051_sc04() throws SQLException, JSchException, IOException {
        User user = new User("UC051AA-IEDR", "Password!", false, "uc051_aa@iedr.ie");
        List<InputField> inputFields = new ArrayList<InputField>();
        inputFields.add(new InputField(By.name("wrapper.address"), "Modified address", "Address"));
        inputFields.add(new InputField(By.cssSelector("#cns_Phone0div input[name='wrapper.phonesWrapper.phone']"),
                "987654321", "Phone"));
        inputFields.add(new InputField(By.name("hostmastersRemark"), "Remark", "Remark"));
        List<SelectField> selectFields = new ArrayList<SelectField>();
        selectFields.add(new SelectField("nic-handle-edit_wrapper_vatCategory", "B", "vat"));
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        emails.add(emailSummaryGenerator.getNhVatStatusChanged(user));
        test_modify_customer_account("UC051AA-IEDR", inputFields, selectFields, true);
    }

    @Test
    public void test_uc051_nosc01() throws SQLException, JSchException, IOException {
        // UC#051: Modify Customer Account Details - Email validation
        crsweb().login(this.internal);
        String nicHandle = "UC051AA-IEDR";
        EditNicHandlePage enhp = new EditNicHandlePage();
        for (String emailAddress : EmailAddressUtils.getInvalidEmailAddressList()) {
            enhp.addInputFieldChange(By.name("wrapper.email"), emailAddress);
            enhp.editNicHandleError(nicHandle, "E-mail is not valid");
        }
    }

    @Test
    public void test_uc051_nosc02() throws SQLException, JSchException, IOException {
        // UC#051: Modify Customer Account Details - Direct with no Mod file created
        User user = new User("UC051AB-IEDR", "Password!", false, "uc051_ab@iedr.ie");
        List<InputField> inputFields = new ArrayList<InputField>();
        inputFields.add(new InputField(By.name("wrapper.address"), "Modified address", "Address"));
        inputFields.add(new InputField(By.cssSelector("#cns_Phone0div input[name='wrapper.phonesWrapper.phone']"),
                "987654321", "Phone"));
        inputFields.add(new InputField(By.name("hostmastersRemark"), "Remark", "Remark"));
        List<SelectField> selectFields = new ArrayList<SelectField>();
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        test_modify_customer_account(user.login, inputFields, selectFields, false);
    }

    private void test_modify_customer_account(String nicHandle, List<InputField> inputFields,
            List<SelectField> selectFields, boolean modFile) throws SQLException, JSchException, IOException {
        modifyNicHandle(nicHandle, inputFields, selectFields);
        checkDb(nicHandle, inputFields, selectFields);
        if (modFile) {
            outputFiles.checkAccountXml(lastAccountId, nicHandle);
        } else {
            assertEquals(db().getLastAccountId(), lastAccountId);
        }
        checkAndResetEmails(emails);
        accountModified = true;
    }

    private void modifyNicHandle(String nicHandle, List<InputField> inputFields, List<SelectField> selectFields) {
        crsweb().login(this.internal);
        EditNicHandlePage enhp = new EditNicHandlePage();
        for (InputField field : inputFields) {
            enhp.addInputFieldChange(field.by, field.value);
        }
        for (SelectField field : selectFields) {
            enhp.addSelectFieldChange(By.id(field.selectId), field.optionValue);
        }
        enhp.editNicHandle(nicHandle);
    }

    private void checkDb(String nicHandle, List<InputField> inputFields, List<SelectField> selectFields)
            throws SQLException {
        for (InputField field : inputFields) {
            if (field.id == "Address") {
                assertEquals(field.value, db().getNicHandleAddress(nicHandle));
            } else if (field.id == "Phone") {
                assertEquals(field.value, db().getNicHandlePhone(nicHandle));
            }
        }
        for (SelectField field : selectFields) {
            if (field.id == "VAT") {
                assertEquals(field.optionValue, db().getVatCategory(nicHandle));
            }
        }
    }

    private class InputField {
        public By by;
        public String value;
        public String id;

        public InputField(By by, String value, String id) {
            this.by = by;
            this.value = value;
            this.id = id;
        }
    }

    private class SelectField {
        private String selectId;
        public String optionValue;
        public String id;

        public SelectField(String selectId, String optionValue, String id) {
            this.selectId = selectId;
            this.optionValue = optionValue;
            this.id = id;
        }
    }

}
