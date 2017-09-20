package com.airhacks.calculator.kpi.boundary;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MetricsCounterTest {

    MetricsCounter cut;

    @Before
    public void init() {
        this.cut = new MetricsCounter();
        this.cut.init();
    }

    @Test
    public void calculateMetrics() {
        this.cut.lastSuccess = 10;
        this.cut.success.set(40);
        this.cut.calculateMetrics();
        assertThat(this.cut.successesPerSecond,is(3d));
        assertThat(this.cut.lastSuccess,is(this.cut.success.longValue()));
    }

}