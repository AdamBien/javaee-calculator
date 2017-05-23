
package com.airhacks.calculator.breaker.boundary;

import com.airhacks.calculator.breaker.entity.SubsequentInvocationsFailure;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.event.Event;
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
    long maxExceptionsThreashold;

    @Inject
    Event<SubsequentInvocationsFailure> unstableServiceEvent;

    @AroundInvoke
    public Object guard(InvocationContext context) throws Exception {
        try {
            int failureCount = COUNTER.get();
            if (failureCount >= this.maxExceptionsThreashold) {
                String methodName = context.getMethod().toString();
                this.unstableServiceEvent.fire(new SubsequentInvocationsFailure(methodName, failureCount));
                throw new UnstableExternalServiceException(methodName);
            }
            return context.proceed();
        } catch (Exception ex) {
            COUNTER.incrementAndGet();
            throw ex;
        }
    }
}
