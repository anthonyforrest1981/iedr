package com.iedr.bpr.tests;

import java.io.IOException;
import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Parameterized;

/*
 * This class was created to enable running single tests in Eclipse/IntelliJ. A regular scenario for this procedure
 * looks like this:
 * 1. Instantiate test class containing the selected test method
 * 2. Filter its methods and run only these, names of which equal to the selected one
 * The problem is, that it's not operating on those methods directly, but on some objects wrapping them and the
 * filtering is performed based on the names returned by those wrappers. In a regular case the name is the same as the
 * method's name, but in case of a parameterized test, the name always has a suffix indicating the value of the
 * parameter. In our case if we're looking for a test method named test_uc001_sc01, we won't find it because it will be
 * called test_uc001_sc01[Firefox].
 *
 * The solution is taken from an answer to the following stackoverflow question:
 * http://stackoverflow.com/questions/12798079/initializationerror-with-eclipse-and-junit4-when-executing-a-single-test
 *
 * The mechanism works properly in IntelliJ 2016.1.3 and using FilterDecorator breaks it, so it is now configurable in
 * testconfig.properties.
 */

public class IdeCompatibleParameterized extends Parameterized {

    private boolean skip = false;

    public IdeCompatibleParameterized(Class<?> klass) throws Throwable {
        super(klass);
        try {
            skip = Boolean.parseBoolean(TestConfig.getConfig().getProperty("skipparameterizedfilter"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        if (skip) {
            super.filter(filter);
        } else {
            super.filter(new FilterDecorator(filter));
        }
    }

    private static class FilterDecorator extends Filter {
        private final Filter delegate;

        private FilterDecorator(Filter delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean shouldRun(Description description) {
            return delegate.shouldRun(wrap(description));
        }

        @Override
        public String describe() {
            return delegate.describe();
        }

        private static Description wrap(Description description) {
            String name = description.getDisplayName();
            String fixedName = deparametrizedName(name);
            Description clonedDescription = Description.createSuiteDescription(fixedName, description.getAnnotations()
                    .toArray(new Annotation[0]));
            for (Description child : description.getChildren()) {
                clonedDescription.addChild(wrap(child));
            }
            return clonedDescription;
        }

    }

    private static String deparametrizedName(String name) {
        if (name.startsWith("[")) {
            return name;
        }

        // Convert <test method name>[<browser name>](<fully-qualified class name>) to
        // <test method name>(<fully-qualified class name>)
        return name.replaceAll("\\[.*\\]", "");
    }

}
