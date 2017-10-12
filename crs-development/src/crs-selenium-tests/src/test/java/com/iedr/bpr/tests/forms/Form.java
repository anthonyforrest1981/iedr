package com.iedr.bpr.tests.forms;

import java.util.List;

public interface Form {
    boolean isPrimitive();

    List<Form> getSubForms();
}
