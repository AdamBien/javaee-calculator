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
    static final String ADDITION_URI = "http://localhost:8282/calculator/resources/operations/";

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
        Response response = this.tut.path("addition").
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(23));
    }

    @Test
    public void slowAddition() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 2).
                add("b", 40).
                build();
        Response response = this.tut.path("addition").
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(23));
    }

    @Test
    public void multiplication() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 2).
                add("b", 21).
                build();
        Response response = this.tut.path("multiplication").
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(42));
    }

    @Test
    public void division() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 22).
                add("b", 2).
                build();
        Response response = this.tut.path("division").
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(11));
    }
    @Test
    public void subtraction() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 22).
                add("b", 2).
                build();
        Response response = this.tut.path("subtraction").
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(200));
        JsonObject jsonResult = response.readEntity(JsonObject.class);
        int result = jsonResult.getJsonNumber("result").intValue();
        assertThat(result, is(19));
    }
}
