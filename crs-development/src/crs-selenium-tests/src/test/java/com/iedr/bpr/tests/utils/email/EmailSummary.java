package com.iedr.bpr.tests.utils.email;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public abstract class EmailSummary {
    public int id;
    public String subject;
    public Set<String> to;
    public Set<String> cc;
    public String text;

    public String toString() {
        String toStr = StringUtils.join(to, ", ");
        String ccStr = StringUtils.join(cc, ", ");
        return String.format("EmailSummary(%d; %s; %s; %s)", id, subject, toStr, ccStr);
    }

}
