package com.airhacks.calculator.kpi.boundary;

import com.airhacks.calculator.breaker.entity.SubsequentInvocationsFailure;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Path("metrics")
public class MetricsCounter {

    ConcurrentHashMap<String,Integer> failedInvocations;
    AtomicLong fortyTwo;
    AtomicLong overflow;
    AtomicLong success;


    double successesPerSecond = 0;
    double overflowsPerSecond = 0;

    long lastSuccess = 0;
    long lastOverflow = 0;

    @PostConstruct
    public void init() {
        this.failedInvocations = new ConcurrentHashMap<>();
        this.fortyTwo = new AtomicLong();
        this.overflow = new AtomicLong();
        this.success = new AtomicLong();
    }

    @GET
    @Path("kpis")
    public JsonObject metrics() {
        return Json.createObjectBuilder().
                add("42count", fortyTwo.longValue()).
                add("overflows", overflow.longValue()).
                add("success", success.longValue()).
                add("successesPerSecond",this.successesPerSecond).
                add("overflowsPerSecond",this.overflowsPerSecond).
                build();
    }

    @GET
    @Path("open-circuits")
    public JsonObject openCircuits() {
        JsonObjectBuilder retVal = Json.createObjectBuilder();
        this.failedInvocations.
                entrySet().
                forEach(entry -> retVal.add(entry.getKey(),entry.getValue()));
        return retVal.build();
    }



    public void onOpenCircuitInvocation(@Observes SubsequentInvocationsFailure failure) {
        String methodName = failure.getMethodName();
        this.failedInvocations.put(methodName,failure.getFailuresCount());
         }

    public void fortyTwo() {
        fortyTwo.incrementAndGet();
    }

    public void overflow() {
        this.overflow.incrementAndGet();
    }

    public void success() {
        this.success.incrementAndGet();
    }

    @Schedule(minute = "*",second = "*/10",hour = "*")
    public void calculateMetrics() {
        System.out.println(".");
        this.successesPerSecond = ((double)(success.longValue() - this.lastSuccess))/10;
        this.lastSuccess = success.longValue();

        this.overflowsPerSecond =  ((double)(overflow.longValue() - this.lastOverflow))/10;
        this.lastOverflow = overflow.longValue();
    }
}
