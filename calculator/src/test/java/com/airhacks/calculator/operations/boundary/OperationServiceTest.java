package com.airhacks.calculator.operations.boundary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationServiceTest {

    OperationService cut;

    @Before
    public void init(){
        this.cut = new OperationService();
    }

    @Test(expected = ArithmeticWebException.class)
    public void divideByZero(){
        this.cut.divide(2,0);

    }

}