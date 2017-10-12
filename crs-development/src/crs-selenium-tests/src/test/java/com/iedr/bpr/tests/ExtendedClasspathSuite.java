package com.iedr.bpr.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ExtendedClasspathSuite extends ClasspathSuite {

    private Class<?> suiteClass;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AfterSuite {
    }

    public ExtendedClasspathSuite(Class<?> suiteClass, RunnerBuilder builder) throws InitializationError {
        super(suiteClass, builder);
        this.suiteClass = suiteClass;
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            super.run(notifier);
        } finally {
            try {
                runAfterMethods();
            } catch (Exception e) {
                notifier.fireTestFailure(new Failure(getDescription(), e));
            }
        }
    }

    private void runAfterMethods() throws Exception {
        for (Method each : suiteClass.getMethods()) {
            if (each.isAnnotationPresent(AfterSuite.class)) {
                if (isPublicStaticVoid(each)) {
                    each.invoke(null, new Object[0]);
                }
            }
        }
    }

    private boolean isPublicStaticVoid(Method method) {
        return method.getReturnType() == void.class && method.getParameterTypes().length == 0
                && (method.getModifiers() & Modifier.STATIC) != 0;
    }

}
