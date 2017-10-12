package pl.nask.crs.app.utils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ValidationHelper {
    private final ActionSupport as;

    /**
     *
     * @param as
     *            ActionSuport object which should receive notification about
     *            the field error
     */
    public ValidationHelper(ActionSupport as) {
        this.as = as;
    }

    /**
     * checks, if the field contains non-empty value
     *
     * @param fieldName
     *            ognl field name to check
     * @return true, if field value is null or empty String, false if String's
     *         length > 0
     */
    public boolean isFieldEmpty(String fieldName) {
        String s = fieldValue(fieldName);
        return isStringEmpty(s);
    }

    /**
     * checks, if the field contains non-empty value. If it doesn't, field error
     * is added and false is returned
     *
     * @param fieldName
     * @param fieldLabel
     * @return
     */
    public boolean validateStringRequired(String fieldName, String fieldLabel) {
        String s = fieldValue(fieldName);
        return validateStringRequired(fieldName, s, fieldLabel);
    }

    private boolean validateStringRequired(String fieldName, String fieldValue, String fieldLabel) {
        if (isStringEmpty(fieldValue)) {
            as.addFieldError(fieldName, "You must enter a value for " + fieldLabel);
            return false;
        }
        return true;
    }

    private boolean isStringEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    private String fieldValue(String fieldName) {
        return ActionContext.getContext().getValueStack().findString(fieldName);
    }
}
