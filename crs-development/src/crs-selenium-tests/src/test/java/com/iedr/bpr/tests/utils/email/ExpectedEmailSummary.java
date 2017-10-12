package com.iedr.bpr.tests.utils.email;

import java.util.HashMap;
import java.util.Map;

public class ExpectedEmailSummary extends EmailSummary {

    public Map<String, String> parameters;
    public Map<String, String> parametersToCheck;

    protected ExpectedEmailSummary(int id, Map<String, String> parameters, Map<String, String> parametersToCheck) {
        this.id = id;
        this.parameters = parameters == null ? new HashMap<String, String>() : parameters;
        this.parametersToCheck = parametersToCheck == null ? new HashMap<String, String>() : parametersToCheck;
    }

    public static class EmailBuilder {

        private Map<String, String> parameters;
        private Map<String, String> parametersToCheck;
        private int eId;

        public EmailBuilder(int eId) {
            parameters = new HashMap<String, String>();
            parametersToCheck = new HashMap<String, String>();
            this.eId = eId;
        }

        public EmailBuilder set(String parameter, String value, boolean verifyPopulation) {
            if (verifyPopulation) {
                parametersToCheck.put(parameter, value);
            }
            parameters.put(parameter, value);
            return this;
        }

        public EmailBuilder set(String parameter, String value) {
            return set(parameter, value, true);
        }

        public ExpectedEmailSummary build() {
            return new ExpectedEmailSummary(eId, parameters, parametersToCheck);
        }

        public EmailBuilder setAdminCEmail(String value) {
            return set("ADMIN-C_EMAIL", value, false);
        }

        public EmailBuilder setAdminCName(String value) {
            return set("ADMIN-C_NAME", value);
        }

        public EmailBuilder setBillCEmail(String value) {
            return set("BILL-C_EMAIL", value, false);
        }

        public EmailBuilder setBillCNic(String value) {
            return set("BILL-C_NIC", value);
        }

        public EmailBuilder setBillCName(String value) {
            return set("BILL-C_NAME", value);
        }

        public EmailBuilder setBillCCoName(String value) {
            return set("BILL-C_CO_NAME", value);
        }

        public EmailBuilder setCreatorCEmail(String value) {
            return set("CREATOR-C_EMAIL", value, false);
        }

        public EmailBuilder setCreatorCName(String value) {
            return set("CREATOR-C_NAME", value);
        }

        public EmailBuilder setDomain(String value) {
            return set("DOMAIN", value);
        }

        public EmailBuilder setDomainHolder(String value) {
            return set("HOLDER", value);
        }

        public EmailBuilder setGainingBillCEmail(String value) {
            return set("GAINING_BILL-C_EMAIL", value, false);
        }

        public EmailBuilder setGainingBillCName(String value) {
            return set("GAINING_BILL-C_NAME", value);
        }

        public EmailBuilder setLosingBillCEmail(String value) {
            return set("LOSING_BILL-C_EMAIL", value, false);
        }

        public EmailBuilder setLosingBillCName(String value) {
            return set("LOSING_BILL-C_NAME", value);
        }

        public EmailBuilder setNic(String value) {
            return set("NIC", value);
        }

        public EmailBuilder setNicEmail(String value) {
            return set("NIC_EMAIL", value, false);
        }

        public EmailBuilder setNicName(String value) {
            return set("NIC_NAME", value);
        }

        public EmailBuilder setRegistrarName(String value) {
            return set("REGISTRAR_NAME", value);
        }

        public EmailBuilder setRemark(String value) {
            return set("REMARK", value);
        }

        public EmailBuilder setTechCEmail(String value) {
            return set("TECH-C_EMAIL", value, false);
        }

        public EmailBuilder setTechCName(String value) {
            return set("TECH-C_NAME", value);
        }

        public EmailBuilder setDaysRemainingWithDaysSuffix(String value) {
            return set("DAYS_REMAINING_WITH_DAYS_SUFFIX", value);
        }

        public EmailBuilder setDaysPassed(String value) {
            return set("DAYS_PASSED", value);
        }
    }

}
