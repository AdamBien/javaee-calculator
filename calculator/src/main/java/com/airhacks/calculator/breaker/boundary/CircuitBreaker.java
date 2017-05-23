
package com.airhacks.calculator.breaker.boundary;

import java.util.concurrent.atomic.AtomicInteger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author airhacks.com
 */
public class CircuitBreaker {

    private AtomicInteger COUNTER = new AtomicInteger();
    private int MAX_EXCEPTIONS_THRESHOLD = 3;

    @AroundInvoke
    public Object guard(InvocationContext context) throws Exception {
        try {
            if (COUNTER.get() >= MAX_EXCEPTIONS_THRESHOLD) {
                throw new UnstableExternalServiceException(context.getMethod().toString());
            }
            return context.proceed();
        } catch (Exception ex) {
            COUNTER.incrementAndGet();
            throw ex;
        }
    }
}
