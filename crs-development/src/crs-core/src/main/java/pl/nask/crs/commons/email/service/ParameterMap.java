package pl.nask.crs.commons.email.service;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

public interface ParameterMap {

    List<? extends ParameterName> getParameterNames();

    /*>>>@Nullable*/ String getParameterValue(ParameterNameEnum parameterName);

}
