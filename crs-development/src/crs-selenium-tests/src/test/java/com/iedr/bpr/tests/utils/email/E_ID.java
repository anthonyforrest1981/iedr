package com.iedr.bpr.tests.utils.email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface E_ID {
    public int value() default 0;

    public boolean configurable() default true;

    public boolean active() default true;
}
