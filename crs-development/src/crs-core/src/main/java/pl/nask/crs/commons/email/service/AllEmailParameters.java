package pl.nask.crs.commons.email.service;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AllEmailParameters implements EmailParameters {

    private long templateId;
    private EmailParameters parameters;

    public AllEmailParameters(long templateId, EmailParameters parameters) {
        this.templateId = templateId;
        this.parameters = parameters;
    }

    public /*>>>@Nullable*/ String getLoggedInNicHandle() {
        return parameters.getLoggedInNicHandle();
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return parameters.getAccountRelatedNicHandle(gaining);
    }

    public String getDomainName() {
        return parameters.getDomainName();
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        if ("TEMPLATE_ID".equals(name)) {
            return "" + templateId;
        } else if ("EMAIL_PARAMETERS".equals(name)) {
            return "" + parameters;
        } else {
            try {
                return parameters.getParameterValue(name, html);
            } catch (Exception e) {
                return "error getting param value: " + e.getMessage();
            }
        }
    }

    @Override
    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean html) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<br>");
        appendValueFor(sb, "TEMPLATE_ID", html);
        appendValueFor(sb, "EMAIL_PARAMETERS", html);
        ParameterNameEnum[] params = ParameterNameEnum.values();
        Arrays.sort(params, new Comparator<ParameterNameEnum>() {

            @Override
            public int compare(ParameterNameEnum o1, ParameterNameEnum o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (ParameterNameEnum p : params) {
            appendValueFor(sb, p.getName(), html);
        }
        sb.append("<br>");
        return sb.toString();
    }

    private void appendValueFor(StringBuilder sb, String name, boolean html) {
        sb.append(name).append(" : ").append(getParameterValue(name, html)).append("\r\n").append("<br>");

    }

}
