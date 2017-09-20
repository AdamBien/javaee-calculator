
package com.airhacks.calculator.breaker.boundary;

import com.airhacks.calculator.breaker.entity.SubsequentInvocationsFailure;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author airhacks.com
 */
public class CircuitBreaker {

    @Inject
    private CircuitBreakersAdmin admin;

    @Inject
    long maxExceptionsThreashold;

    @Inject
    Event<SubsequentInvocationsFailure> unstableServiceEvent;

    @AroundInvoke
    public Object guard(InvocationContext context) throws Exception {
        Object target = context.getTarget();
        AtomicInteger counter = admin.getCounter(target);
        try {
            int failureCount = counter.get();
            if (failureCount >= this.maxExceptionsThreashold) {
                String methodName = context.getMethod().toString();
                this.unstableServiceEvent.fire(new SubsequentInvocationsFailure(methodName, failureCount));
                throw new UnstableExternalServiceException(methodName);
            }
            return context.proceed();
        } catch (Exception ex) {
            counter.incrementAndGet();
            throw ex;
        }
    }
}
