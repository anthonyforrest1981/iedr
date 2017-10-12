package com.iedr.bpr.tests.utils.email;

public abstract class DetailedExpectedEmailSummary extends ExpectedEmailSummary {

    public ExpectedEmailSummary base;

    public DetailedExpectedEmailSummary(ExpectedEmailSummary base) {
        super(base.id, base.parameters, base.parametersToCheck);
        this.base = base;
    }

    public abstract boolean matches(ActualEmailSummary actualEmail);

    public static class MatchEverything extends DetailedExpectedEmailSummary {

        public MatchEverything(ExpectedEmailSummary base) {
            super(base);
        }

        public boolean matches(ActualEmailSummary actualEmail) {
            return true;
        }

    }

    public static class SubjectContains extends DetailedExpectedEmailSummary {

        private String substring;

        public SubjectContains(ExpectedEmailSummary base, String substring) {
            super(base);
            this.substring = substring;
        }

        @Override
        public boolean matches(ActualEmailSummary actualEmail) {
            return actualEmail.subject.contains(substring);
        }

    }

    public static class BodyContains extends DetailedExpectedEmailSummary {

        private String substring;

        public BodyContains(ExpectedEmailSummary base, String substring) {
            super(base);
            this.substring = substring;
        }

        @Override
        public boolean matches(ActualEmailSummary actualEmail) {
            return actualEmail.body.contains(substring);
        }

    }

}
