package com.airhacks.calculator.operations.boundary;

import com.airhacks.calculator.kpi.boundary.MetricsCounter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OperationServiceTest {

    OperationService cut;

    @Before
    public void init(){
        this.cut = new OperationService();
        this.cut.counter = mock(MetricsCounter.class);
    }

    @Test(expected = ArithmeticWebException.class)
    public void divideByZero(){
        this.cut.divide(2,0);

    }

}