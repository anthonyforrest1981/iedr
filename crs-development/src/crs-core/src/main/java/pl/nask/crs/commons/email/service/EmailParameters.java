package pl.nask.crs.commons.email.service;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

public interface EmailParameters {

    List<? extends ParameterName> getParameterNames();

    /*>>>@Nullable*/ String getParameterValue(String name, boolean html);

    /*>>>@Nullable*/ String getLoggedInNicHandle();

    String getAccountRelatedNicHandle(boolean gaining);

    String getDomainName();

}
