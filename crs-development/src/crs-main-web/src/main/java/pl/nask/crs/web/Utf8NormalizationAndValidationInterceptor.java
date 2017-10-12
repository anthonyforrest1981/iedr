package pl.nask.crs.web;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.ValidationException;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.utils.Validator;

public class Utf8NormalizationAndValidationInterceptor extends AbstractInterceptor {

    private static final String INVALID_CHARACTER_MESSAGE = "Provided input contains non-BMP characters";

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
        boolean valid = parameters == null || normalizeAndValidateParameters(parameters, invocation);
        return valid ? invocation.invoke() : Action.INPUT;
    }

    private boolean normalizeAndValidateParameters(Map<String, Object> parameters, ActionInvocation invocation)
            throws ValidationException {
        DelegatingValidatorContext context = new DelegatingValidatorContext(invocation.getAction());
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            String parameterName = parameter.getKey();
            Object parameterValue = parameter.getValue();
            try {
                String[] parameterValues = (String[]) parameterValue;
                boolean valid = normalizeAndValidateParameterValues(parameterValues);
                if (!valid) {
                    context.addFieldError(parameterName, INVALID_CHARACTER_MESSAGE);
                }
                parameters.put(parameterName, parameterValues);
            } catch (ClassCastException e) {
                Logger.getLogger(Utf8NormalizationAndValidationInterceptor.class).error(
                        String.format("Parameter of unexpected class occured: %s", parameterValue));
            }
        }
        if (context.hasErrors()) {
            // Not all fields will display error message. In this case we want to present an action error.
            context.addActionError(INVALID_CHARACTER_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private boolean normalizeAndValidateParameterValues(String[] parameterValues) {
        boolean valid = true;
        for (int i = 0; i < parameterValues.length; i++) {
            String parameterValue = parameterValues[i];
            try {
                parameterValues[i] = Validator.getNormalizedAndValidatedUtf8(parameterValue);
            } catch (IncorrectUtf8FormatException e) {
                valid = false;
            }
        }
        return valid;
    }

}
