package pl.nask.crs.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class UppercaseInterceptor extends MethodFilterInterceptor {
    private Set<String> fieldNames = new HashSet<String>();

    public void setFieldNames(String params) {
        fieldNames = InterceptorHelper.asCollection(params);
    }

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> map = invocation.getInvocationContext().getParameters();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            if (fieldNames.contains(e.getKey()) && (e.getValue() instanceof String[])) {
                String[] val = (String[]) e.getValue();
                if (val != null) {
                    for (int i = 0; i < val.length; i++) {
                        val[i] = val[i] != null ? val[i].toUpperCase() : null;
                    }
                }
            }
        }
        return invocation.invoke();
    }

}
