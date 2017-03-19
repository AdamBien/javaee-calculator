/*
 */
package com.airhacks.calculator.operations.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class OperationsResourceIT {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://localhost:8080/calculator/resources/operations/addition";

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(ADDITION_URI);
    }

    @Test
    public void addition() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 2).
                add("b", 21).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(23));
    }
}
