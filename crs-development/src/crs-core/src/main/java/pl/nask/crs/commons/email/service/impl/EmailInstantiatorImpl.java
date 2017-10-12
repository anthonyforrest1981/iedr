package pl.nask.crs.commons.email.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.utils.Validator;

public class EmailInstantiatorImpl implements EmailInstantiator {

    private static final String DELIM = "$";

    /**
     * @param template
     * @param parameters
     * @return Email with valid addressee list
     * @throws pl.nask.crs.commons.email.service.TemplateInstantiatingException
     */
    public Email instantiate(EmailTemplate template, EmailParameters parameters, boolean shouldBeSentToExternalUser)
            throws TemplateInstantiatingException {
        Validator.assertNotNull(parameters, "template parameters");
        Email email = new Email();
        email.setSubject(instantiateText(template.getSubject(), parameters));
        email.setText(instantiateText(template.getText(), parameters, template.isHtml()));
        if (template.isHtml()) {
            email.setText(email.getText().replaceAll("\r", "").replaceAll("\n", "<br/>"));
        }
        // ----- test
        email.setSubject(email.getSubject().replace(DELIM + "TEMPLATE_ID" + DELIM, "" + template.getId()));
        email.setText(email.getText().replace(DELIM + "TEMPLATE_ID" + DELIM, "Template ID: " + template.getId()));
        email.setText(email.getText().replace(DELIM + "ALL_EMAIL_PARAMETERS" + DELIM,
                new AllEmailParameters(template.getId(), parameters).toString(template.isHtml())));
        // ----- test end
        Set<String> toList = instantiateContacts(template.getInternalToList(), parameters);
        Set<String> ccList = instantiateContacts(template.getInternalCcList(), parameters);
        Set<String> bccList = instantiateContacts(template.getInternalBccList(), parameters);

        if (shouldBeSentToExternalUser) {
            toList.addAll(instantiateContacts(template.getToList(), parameters));
            ccList.addAll(instantiateContacts(template.getCcList(), parameters));
            bccList.addAll(instantiateContacts(template.getBccList(), parameters));
        }
        ccList.removeAll(toList);
        bccList.removeAll(toList);
        bccList.removeAll(ccList);
        email.setToList(toList);
        email.setCcList(ccList);
        email.setBccList(bccList);
        email.setFrom(instantiateText(template.getMailSmtpFrom(), parameters));
        email.setActive(template.isActive());
        email.setHtml(template.isHtml());
        email.setTemplateId(template.getId());
        //validateEmail(email);
        return email;
    }

    /* The validation is out of point after email disabler implementation */
    /*private void validateEmail(Email email) throws TemplateInstantiatingException {
       if ((email.getToList().isEmpty()) && (email.getCcList().isEmpty()) && (email.getBccList().isEmpty()))
           //throw new TemplateInstantiatingException("No addressee in template.");
           email.setActive(false);
    }*/

    private String instantiateText(String text, EmailParameters parameters) {
        return instantiateText(text, parameters, false);
    }

    private String instantiateText(String text, EmailParameters parameters, boolean html) {
        if (text == null)
            return "";
        if (!checkIfNeedInstantiation(text))
            return text;
        List<? extends ParameterName> paramNames = parameters.getParameterNames();
        /*
         * REFACTORME:
         * TODO: this should be refactored: this approach makes lazy loading of param values impossible.
         *
         * It would be much better if the email template was first parsed to gather all parameters (this will make it possible to log usage of unsupported parameters)
         * and then to replace them with the corresponding values
         * (and again: it will be possible to log parameters which have no value).
         */

        for (ParameterName name : paramNames) {
            String s = parameters.getParameterValue(name.getName(), html);
            if (s == null)
                s = "";
            text = text.replace(DELIM + name.getName() + DELIM, s);
        }
        return text;
    }

    private boolean checkIfNeedInstantiation(String text) {
        return text.contains(DELIM);
    }

    private Set<String> instantiateContacts(List<String> contacts, EmailParameters parameters) {
        if (contacts == null)
            return new LinkedHashSet<>();
        Set<String> result = new LinkedHashSet<>();
        for (String contact : contacts) {
            String s = instantiateText(contact, parameters);
            if (!Validator.isEmpty(s)) {
                for (String email : s.split(",")) {
                    result.add(email);
                }
            }
        }
        return result;
    }

}
