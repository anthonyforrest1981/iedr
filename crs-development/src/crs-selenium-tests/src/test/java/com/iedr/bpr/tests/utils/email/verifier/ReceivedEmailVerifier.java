package com.iedr.bpr.tests.utils.email.verifier;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReceivedEmailVerifier {

    private int eId;
    private ExpectedEmailSummary expectedEmail;
    private ActualEmailSummary actualEmail;
    private EmailTemplate template;

    private static final List<String> dbColumns = Arrays.asList("E_Subject", "E_To", "E_To_Internal", "E_CC",
            "E_CC_Internal", "E_Text");

    public static enum ReceivedEmailType {
        INTERNAL, EXTERNAL, BOTH
    }

    public ReceivedEmailVerifier(ExpectedEmailSummary expectedEmail, ActualEmailSummary actualEmail)
            throws SQLException {
        this.expectedEmail = expectedEmail;
        this.actualEmail = actualEmail;
        this.eId = expectedEmail.id;
        Map<String, String> values = fetchDbValues();
        this.template = getEmailTemplate(values);
    }

    public void verify() {
        verifyReceivers();
        checkPopulatedParameters();
    }

    public ReceivedEmailType getReceiverType(ActualEmailSummary actualEmail) {
        boolean internal = false;
        boolean external = false;
        if (actualEmail.to.equals(template.toI) && actualEmail.cc.equals(template.ccI)) {
            internal = true;
        }
        if (actualEmail.to.equals(template.to) && actualEmail.cc.equals(template.cc)) {
            external = true;
        }
        if (internal && external) {
            return ReceivedEmailType.BOTH;
        } else if (internal) {
            return ReceivedEmailType.INTERNAL;
        } else if (external) {
            return ReceivedEmailType.EXTERNAL;
        } else {
            throw new RuntimeException(String.format("Couldn't establish type of sent email E_ID=%s (%s)", eId,
                    actualEmail));
        }
    }

    public Map<String, String> getPopulatedParameters() {
        return template.getPopulatedParameters(actualEmail);
    }

    private void verifyReceivers() {
        String errorMsg = String.format("E_ID=%s", eId);
        assertEquals(errorMsg, template.to, actualEmail.to);
        assertEquals(errorMsg, template.cc, actualEmail.cc);
    }

    private Map<String, String> fetchDbValues() throws SQLException {
        Map<String, String> valuesMap = new HashMap<String, String>();
        List<String> values = db().getEmailTemplateValues(eId, dbColumns);
        for (int i = 0; i < values.size(); i++) {
            valuesMap.put(dbColumns.get(i), values.get(i));
        }
        return valuesMap;
    }

    private EmailTemplate getEmailTemplate(Map<String, String> values) {
        EmailTemplate template = new EmailTemplate(eId, values);
        template.populateReceivers(expectedEmail);
        return template;
    }

    private void checkPopulatedParameters() {
        Map<String, String> populatedParameters = template.getPopulatedParameters(actualEmail);
        for (String parameter : expectedEmail.parametersToCheck.keySet()) {
            assertTrue(String.format("Checking parameter %s in email " + "E_ID=%s failed (populated paramteres: %s)",
                    parameter, eId, populatedParameters.keySet()), populatedParameters.containsKey(parameter));
            assertEquals(String.format("E_ID=%s parameter %s check failed", eId, parameter),
                    expectedEmail.parametersToCheck.get(parameter), populatedParameters.get(parameter));
        }
    }

}
