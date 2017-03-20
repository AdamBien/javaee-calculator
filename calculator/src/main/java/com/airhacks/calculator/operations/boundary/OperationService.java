
package com.airhacks.calculator.operations.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class OperationService {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://addition:8080/addition/resources/addition/";

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(ADDITION_URI);
    }

    public int add(int a, int b) {
        JsonObject input = Json.createObjectBuilder().
                add("a", 2).
                add("b", 21).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        return jsonResult.getJsonNumber("result").intValue();
    }
}
