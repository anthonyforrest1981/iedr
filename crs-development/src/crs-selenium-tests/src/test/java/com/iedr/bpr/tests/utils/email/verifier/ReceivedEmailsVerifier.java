package com.iedr.bpr.tests.utils.email.verifier;

import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.EmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReceivedEmailsVerifier {

    private Set<ExpectedEmailSummary> expectedEmails;
    private Set<ActualEmailSummary> actualEmails;
    private Map<Integer, List<ActualEmailSummary>> actualEmailsMap;

    public ReceivedEmailsVerifier(Set<ExpectedEmailSummary> expectedEmails, Set<ActualEmailSummary> actualEmails) {
        this.expectedEmails = expectedEmails;
        this.actualEmails = actualEmails;
        this.actualEmailsMap = createEmailsMap(actualEmails);
    }

    public void verify(boolean checkUnexpected) throws SQLException {
        checkAllExpectedReceived();
        if (checkUnexpected) {
            assertEquals(getUnexpectedEmailsMessage(), expectedEmails.size(), actualEmails.size());
        }
    }

    public ActualEmailSummary getActualEmail(ExpectedEmailSummary expectedEmail) {
        Set<Integer> actualEmailIds = actualEmailsMap.keySet();
        String emailNotFoundMessage = String.format("Email with ID=%s not found. " + "Received: %s",
                expectedEmail.id, actualEmails);
        assertTrue(emailNotFoundMessage, actualEmailIds.contains(expectedEmail.id));
        List<ActualEmailSummary> actualEmailsForId = actualEmailsMap.get(expectedEmail.id);
        ActualEmailSummary actualEmail;

        if (actualEmailsForId.size() == 1) {
            actualEmail = actualEmailsForId.get(0);
        } else {
            int i = getMatchingEmailIndex(expectedEmail, actualEmailsForId);
            actualEmail = actualEmailsForId.get(i);
            actualEmailsForId.remove(i);
        }
        return actualEmail;
    }

    public static Set<Integer> getEmailIds(Set<? extends EmailSummary> emails) {
        Set<Integer> emailIds = new TreeSet<Integer>();
        for (EmailSummary email : emails) {
            emailIds.add(email.id);
        }
        return emailIds;
    }

    private void checkAllExpectedReceived() throws SQLException {
        for (ExpectedEmailSummary expectedEmail : expectedEmails) {
            ActualEmailSummary actualEmail = getActualEmail(expectedEmail);
            ReceivedEmailVerifier ev = new ReceivedEmailVerifier(expectedEmail, actualEmail);
            ev.verify();
        }
    }

    private int getMatchingEmailIndex(ExpectedEmailSummary expectedEmail, List<ActualEmailSummary> actualEmailsForId) {
        DetailedExpectedEmailSummary detailedEmail;
        try {
            detailedEmail = (DetailedExpectedEmailSummary) expectedEmail;
        } catch (ClassCastException e) {
            String message = String.format("There were multiple (%s) emails with E_ID=%s for this test, but no " +
                    "matching function has been provided.", actualEmailsForId.size(), expectedEmail.id);
            for (ActualEmailSummary email : actualEmailsForId) {
                message += String.format("\n%s\n%s", email.toString(), email.text);
            }
            throw new AssertionError(message);
        }
        Integer matchingEmailIndex = null;
        for (int i = 0; i < actualEmailsForId.size(); i++) {
            if (detailedEmail.matches(actualEmailsForId.get(i))) {
                matchingEmailIndex = i;
                break;
            }
        }
        assertTrue(String.format("Expected email %s did not match any of " + "received emails with same E_ID",
                expectedEmail), matchingEmailIndex != null);
        return matchingEmailIndex;
    }

    private Map<Integer, List<ActualEmailSummary>> createEmailsMap(Set<ActualEmailSummary> actualEmails) {
        Map<Integer, List<ActualEmailSummary>> map = new HashMap<Integer, List<ActualEmailSummary>>();
        for (ActualEmailSummary email : actualEmails) {
            if (!map.containsKey(email.id)) {
                map.put(email.id, new ArrayList<ActualEmailSummary>());
            }
            map.get(email.id).add(email);
        }
        return map;
    }

    private String getUnexpectedEmailsMessage() {
        Set<Integer> expectedEmailIds = getEmailIds(expectedEmails);
        Set<Integer> actualEmailIds = getEmailIds(actualEmails);
        actualEmailIds.removeAll(expectedEmailIds);
        return String.format("Received %d unexpected email(s) with ID(s): %s", actualEmailIds.size(),
                StringUtils.join(actualEmailIds, ", "));
    }

}
