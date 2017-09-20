package com.airhacks.calculator.kpi.boundary;

import javax.ejb.*;
import javax.faces.bean.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.ParameterMetaData;
import java.util.concurrent.atomic.AtomicLong;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Path("metrics")
public class MetricsCounter {

     AtomicLong FOURTY_TWO = new AtomicLong();
     AtomicLong OVERFLOW = new AtomicLong();
     AtomicLong SUCCESS = new AtomicLong();

     double successesPerSecond = 0;
     double overflowsPerSecond = 0;

     long lastSuccess = 0;
     long lastOverflow = 0;

    @GET
    public JsonObject metrics() {
        return Json.createObjectBuilder().
                add("42count",FOURTY_TWO.longValue()).
                add("overflows",OVERFLOW.longValue()).
                add("success",SUCCESS.longValue()).
                add("successesPerSecond",this.successesPerSecond).
                add("overflowsPerSecond",this.overflowsPerSecond).
                build();
    }

    public void fourtyTwo() {
        FOURTY_TWO.incrementAndGet();
    }

    public void overflow() {
        this.OVERFLOW.incrementAndGet();
    }

    public void success() {
        this.SUCCESS.incrementAndGet();
    }

    @Schedule(minute = "*",second = "*/10",hour = "*")
    public void calculateMetrics() {
        this.successesPerSecond = (SUCCESS.longValue() - this.lastSuccess)/10;
        this.lastSuccess = SUCCESS.longValue();

        this.overflowsPerSecond = (OVERFLOW.longValue() - this.lastSuccess)/10;
        this.lastOverflow = OVERFLOW.longValue();
    }


}
