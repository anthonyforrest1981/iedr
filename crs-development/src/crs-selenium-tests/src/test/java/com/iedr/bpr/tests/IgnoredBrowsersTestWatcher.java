package com.iedr.bpr.tests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IgnoredBrowsersTestWatcher extends TestWatcher {
    private IgnoredBrowsers ignoredBrowsers;

    @Override
    protected void starting(Description description) {
        ignoredBrowsers = description.getAnnotation(IgnoredBrowsers.class);
    }

    public List<SeleniumTest.Browser> getIgnoredBrowsers() {
        if (ignoredBrowsers == null) {
            return Collections.emptyList();
        }
        SeleniumTest.Browser[] eId = ignoredBrowsers.value();
        return Arrays.asList(eId);
    }

}
