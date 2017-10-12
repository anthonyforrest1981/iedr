package com.iedr.bpr.tests.utils.email;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmailUtils {

    public static Set<String> getValueAsSet(String value) {
        Set<String> values = new HashSet<String>();
        if (value != null && !value.trim().equals("")) {
            value = value.trim();
            values = new HashSet<String>(Arrays.asList(value.split("\\s*,\\s*")));
        }
        return values;
    }

}
