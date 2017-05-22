/*
 */
package com.airhacks.calculator.addition.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class AdditionResourceIT {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://localhost:8383/addition/resources/addition/";

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

    @Test
    public void additionWithIncompleteInput() {
        JsonObject input = Json.createObjectBuilder().
                add("b", 2).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(400));
        String cause = response.getHeaderString("cause");
        assertThat(cause, containsString(" a "));

        input = Json.createObjectBuilder().
                add("a", 2).
                build();
        response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(400));
        cause = response.getHeaderString("cause");
        assertThat(cause, containsString(" b "));
    }

    @Test
    public void timeoutWith42() {
        JsonObject input = Json.createObjectBuilder().
                add("a", 40).
                add("b", 2).
                build();
        Response response = this.tut.
                request(MediaType.APPLICATION_JSON).
                post(json(input));
        assertThat(response.getStatus(), is(503));
        String reason = response.getHeaderString("reason");
        assertThat(reason, containsString("lazy"));
    }

}
