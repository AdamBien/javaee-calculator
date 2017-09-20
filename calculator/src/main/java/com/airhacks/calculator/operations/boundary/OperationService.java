
package com.airhacks.calculator.operations.boundary;

import com.airhacks.calculator.breaker.boundary.CircuitBreaker;
import com.airhacks.interceptor.monitoring.boundary.PerformanceSensor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Interceptors({PerformanceSensor.class,CircuitBreaker.class})
public class OperationService {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://addition:8080/addition/resources/addition/";

    @Inject
    private long readTimeout;
    @Inject
    private long connectionTimeout;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.client.property("http.connection.timeout", this.connectionTimeout);
        this.client.property("http.receive.timeout", this.readTimeout);
        this.tut = this.client.target(ADDITION_URI);
    }

    public int add(int a, int b) {
        JsonObject input = Json.createObjectBuilder().
                add("a", a).
                add("b", b).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        return jsonResult.getJsonNumber("result").intValue();
    }

    public double divide(int a,int b){
        return a / b;
    }

    public int multiply(int a,int b){
        return a*b;
    }

    public int substract(int a,int b){
        return a-b;
    }
}
