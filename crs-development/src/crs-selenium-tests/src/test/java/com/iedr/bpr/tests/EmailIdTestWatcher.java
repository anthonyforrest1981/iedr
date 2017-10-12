package com.iedr.bpr.tests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.iedr.bpr.tests.utils.email.E_ID;

public class EmailIdTestWatcher extends TestWatcher {
    private E_ID emailId;

    @Override
    protected void starting(Description description) {
        emailId = description.getAnnotation(E_ID.class);
    }

    public E_ID getEmailIdAnnotation() {
        return emailId;
    }

    public Integer getEmailId() {
        if (emailId == null) {
            return null;
        }
        int eId = emailId.value();
        return eId == 0 ? null : eId;
    }

    public boolean isConfigurable() {
        if (emailId == null) {
            return true;
        }
        return emailId.configurable();
    }

    public boolean isActive() {
        if (emailId == null) {
            return false;
        }
        return emailId.active();
    }
}
