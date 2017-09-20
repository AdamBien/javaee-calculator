package com.airhacks.calculator.breaker.boundary;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@Path("circuit-breakers")
public class CircuitBreakersAdmin {

    ConcurrentHashMap<String,AtomicInteger> counters;

    @PostConstruct
    public void init() {
        this.counters = new ConcurrentHashMap<>();
    }

    public AtomicInteger getCounter(Object target) {
        String name = target.getClass().getName();
        this.counters.putIfAbsent(name, new AtomicInteger());
        return this.counters.get(name);
    }

    @GET
    public JsonObject all() {
        JsonObjectBuilder retVal = Json.createObjectBuilder();
        this.counters.entrySet().forEach(e-> retVal.add(e.getKey(),e.getValue().longValue()));
        return retVal.build();
    }

    @GET
    @Path("{name}")
    public Response find(@PathParam("name") String name) {
        if(!this.counters.containsKey(name))
            return Response.noContent().build();
        AtomicInteger atomicLong = this.counters.get(name);
        return Response.ok(atomicLong.longValue()).build();
    }

    @DELETE
    @Path("{name}")
    public void reset(@PathParam("name") String name) {
        AtomicInteger atomicLong = this.counters.get(name);
        if(atomicLong != null)
            atomicLong.set(0);
    }

    @DELETE
    public void resetAll() {
        this.counters.values().forEach(v -> v.set(0));
    }
}
