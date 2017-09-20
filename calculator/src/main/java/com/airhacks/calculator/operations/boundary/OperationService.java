
package com.airhacks.calculator.operations.boundary;

import com.airhacks.calculator.breaker.boundary.CircuitBreaker;
import com.airhacks.calculator.kpi.boundary.MetricsCounter;
import com.airhacks.interceptor.monitoring.boundary.PerformanceSensor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static java.lang.Math.multiplyExact;
import static java.lang.Math.subtractExact;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Interceptors(PerformanceSensor.class)
public class OperationService {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://addition:8080/addition/resources/addition/";

    @Inject
    private long readTimeout;
    @Inject
    private long connectionTimeout;

    @Inject
    MetricsCounter counter;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.client.property("http.connection.timeout", this.connectionTimeout);
        this.client.property("http.receive.timeout", this.readTimeout);
        this.tut = this.client.target(ADDITION_URI);
    }

    @Interceptors(CircuitBreaker.class)
    public int add(int a, int b) {
        JsonObject input = Json.createObjectBuilder().
                add("a", a).
                add("b", b).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        this.counter.success();
        return jsonResult.getJsonNumber("result").intValue();
    }

    public double divide(int a,int b){
        try {
            return verifyResult(a / b);
        }catch(ArithmeticException ex){
            this.counter.overflow();
            throw new ArithmeticWebException(ex.getMessage());
        }
    }

    public int multiply(int a,int b){
        try {
            return verifyResult(multiplyExact(a,b));
        } catch (ArithmeticException e) {
            this.counter.overflow();
            throw new ArithmeticWebException(e.getMessage());
        }
    }

    public int substract(int a,int b){
        try {
            return verifyResult(subtractExact(a,b));
        } catch (ArithmeticException e) {
            this.counter.overflow();
            throw new ArithmeticWebException(e.getMessage());
        }
    }

     <T extends Number> T verifyResult(T number){
        if(number.intValue() == 42){
            this.counter.fortyTwo();
        }
        this.counter.success();
        return number;
    }


}
