package com.iedr.bpr.tests.utils.email.verifier;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.EmailUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailTemplate {

    public Set<String> to;
    public Set<String> cc;
    public Set<String> toI;
    public Set<String> ccI;
    private int eId;
    private String subject;
    private String text;

    private Map<String, String> values;

    public EmailTemplate(int eId, Map<String, String> values) {
        this.eId = eId;
        this.values = values;
        this.subject = getSubject(values);
        this.text = getText(values);
    }

    public void populateReceivers(ExpectedEmailSummary expectedEmail) {
        String toTemplate = joinRecipients(values.get("E_To"), values.get("E_To_Internal"));
        String ccTemplate = joinRecipients(values.get("E_CC"), values.get("E_CC_Internal"));
        to = EmailUtils.getValueAsSet(populateValue(toTemplate, expectedEmail.parameters, false));
        cc = EmailUtils.getValueAsSet(populateValue(ccTemplate, expectedEmail.parameters, false));
        toI = EmailUtils.getValueAsSet(populateValue(values.get("E_To_Internal"), expectedEmail.parameters, false));
        ccI = EmailUtils.getValueAsSet(populateValue(values.get("E_CC_Internal"), expectedEmail.parameters, false));
        cc.removeAll(to);
        ccI.removeAll(toI);
    }

    public Map<String, String> getPopulatedParameters(ActualEmailSummary actualEmail) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.putAll(getPopulatedParameters(subject, actualEmail.subject));
        parameters.putAll(getPopulatedParameters(text, actualEmail.text));
        return parameters;
    }

    private String getSubject(Map<String, String> values) {
        return values.get("E_Subject").trim();
    }

    private String getText(Map<String, String> values) {
        return values.get("E_Text").replaceAll("\r\n?", "\n");
    }

    private Map<String, String> getPopulatedParameters(String templateValue, String actualValue) {
        checkUnpopulatedParameters(actualValue);
        Map<String, String> populatedParameters = new HashMap<String, String>();
        List<String> parameterNames = getParameterNames(templateValue);
        String regex = templateValueAsRegEx(templateValue);
        Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(actualValue);
        assertEquals(String.format("Email E_ID=%s parameters count differ from regex groups\n"
                + "parameters:%s\n\ngroups:%s", eId, parameterNames, m.groupCount()), parameterNames.size(),
                m.groupCount());
        try {
            assertTrue(m.matches());
        } catch (AssertionError e) {
            // Easier to view differences in eclipse.
            String message = String.format("Email E_ID=%s didn't match its template\n" + "TEMPLATE:\n%s\nACTUAL:\n%s",
                    eId, templateValue, actualValue);
            assertEquals(message, templateValue, actualValue);
        }

        for (int i = 0; i < m.groupCount(); i++) {
            String name = parameterNames.get(i);
            String value = m.group(i + 1);
            assertFalse(String.format("Parameter %s in email E_ID=%s " + "populated as empty string", name, eId),
                    "".equals(value));
            if (populatedParameters.containsKey(name)) {
                String prevValue = populatedParameters.get(name);
                assertEquals(String.format("Parameter %s in email E_ID=%s populated differently: %s and %s", name, eId,
                        prevValue, value), prevValue, value);
            }
            populatedParameters.put(name, value);
        }
        return populatedParameters;
    }

    private void checkUnpopulatedParameters(String value) {
        List<String> parameterNames = getParameterNames(value);
        String message = String.format("Email E_ID=%s had unpopulated parameters: %s", eId, parameterNames);
        assertEquals(message, 0, parameterNames.size());
    }

    private List<String> getParameterNames(String value) {
        List<String> names = new ArrayList<String>();
        Matcher m = Pattern.compile("\\$([\\w-]+)\\$").matcher(value);
        while (m.find()) {
            names.add(m.group(1));
        }
        return names;
    }

    private String templateValueAsRegEx(String value) {
        // We want to replace all occurrences of $SOMETHING$ with ".+". The rest
        // of the field may contain restricted RegEx characters, so we have to
        // enclose it in \Q\E block. "Some field with $PARAMETER$ and more" will
        // become "\QSome field with \E.+\Q and more\E".
        value = value.replaceAll("\\$[\\w-]+\\$", "\\\\E(.*)\\\\Q");
        if (value.endsWith(" ")) {
            // TODO: we might want to debug it. Looks like in some cases the
            // trailing space is received in the actual email, but not always.
            value = value.substring(0, value.length() - 1);
            value = String.format("\\Q%s\\E ?", value);
        } else {
            value = String.format("\\Q%s\\E", value);
        }
        return value;
    }

    private String populateValue(String value, Map<String, String> parameters, boolean ignoreMissingParameters) {
        if (value == null) {
            return value;
        }
        Matcher m = Pattern.compile("\\$([^\\$]+)\\$").matcher(value);
        while (m.find()) {
            String parameter = m.group(1);
            if (parameters.containsKey(parameter)) {
                String fullParameter = String.format("$%s$", parameter);
                value = value.replace(fullParameter, parameters.get(parameter));
            } else if (!ignoreMissingParameters) {
                throw new RuntimeException(String.format("Email (E_ID=%s) parameter %s "
                        + "not found in parameters map", eId, parameter));
            }
        }
        return value;
    }

    private String joinRecipients(String... recipients) {
        List<String> list = new ArrayList<String>();
        for (String recipient : Arrays.asList(recipients)) {
            if (recipient == null) {
                continue;
            }
            // Some addresses in DB contain trailing commas.
            list.add(recipient.replaceAll(",$", ""));
        }
        return StringUtils.join(list, ",");
    }

}
