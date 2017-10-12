package pl.nask.crs.api.validation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Utf8ValidatorAspect {

    @Pointcut("execution(public * pl.nask.crs.api..*(..))")
    public void isPublicApiMethod() {}

    @Pointcut("within(pl.nask.crs.api..*Endpoint)")
    public void isEndpoint() {}

    @Pointcut("bean(CRS*)")
    public void isCRSBean() {}

    @Pointcut("bean(*Proxy)")
    public void isProxyBean() {}

    @Around("isCRSBean() && !isProxyBean() && isPublicApiMethod()")
    public Object validateAndNormalizeArguments(ProceedingJoinPoint pjp) throws Throwable {
        Object[] origArguments = pjp.getArgs();
        Object[] normalizedArguments = new Object[origArguments.length];
        int i = 0;
        for (Object o : origArguments) {
            normalizedArguments[i++] = Utf8Validator.validated(o);
        }
        return pjp.proceed(normalizedArguments);
    }

}
