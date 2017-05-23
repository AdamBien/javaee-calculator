
package com.airhacks.calculator.breaker.entity;

/**
 *
 * @author airhacks.com
 */
public class SubsequentInvocationsFailure {

    private String methodName;
    private int failuresCount;

    public SubsequentInvocationsFailure(String methodName, int failuresCount) {
        this.methodName = methodName;
        this.failuresCount = failuresCount;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getFailuresCount() {
        return failuresCount;
    }
}
