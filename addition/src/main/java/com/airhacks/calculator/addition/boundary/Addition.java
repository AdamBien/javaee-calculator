
package com.airhacks.calculator.addition.boundary;

import javax.ejb.Stateless;

@Stateless
public class Addition {

    public int add(int a, int b) {
        return a + b;
    }
}
