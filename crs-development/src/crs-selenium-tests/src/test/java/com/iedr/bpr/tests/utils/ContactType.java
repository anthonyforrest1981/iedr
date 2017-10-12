package com.iedr.bpr.tests.utils;

public enum ContactType {
    BILL("Billing Contact"), ADMIN("Admin Contact"), TECH("Tech Contact");

    private final String type;

    private ContactType(final String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
