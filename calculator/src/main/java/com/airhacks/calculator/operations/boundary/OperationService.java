
package com.airhacks.calculator.operations.boundary;

import javax.ejb.Stateless;

@Stateless
public class OperationService {

    public int add(int a, int b) {
        return a + b;
    }
}
