
package com.airhacks.calculator.breaker.boundary;

import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author airhacks.com
 */
public class CircuitBreaker {

    private AtomicInteger COUNTER = new AtomicInteger();

    @Inject
    private long maxExceptionsThreashold;

    @AroundInvoke
    public Object guard(InvocationContext context) throws Exception {
        try {
            if (COUNTER.get() >= this.maxExceptionsThreashold) {
                throw new UnstableExternalServiceException(context.getMethod().toString());
            }
            return context.proceed();
        } catch (Exception ex) {
            COUNTER.incrementAndGet();
            throw ex;
        }
    }
}
