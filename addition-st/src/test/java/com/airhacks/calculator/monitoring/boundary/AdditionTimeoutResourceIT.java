
package com.airhacks.calculator.monitoring.boundary;

import com.airhacks.calculator.addition.boundary.AdditionResourceIT;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class AdditionTimeoutResourceIT {

    private Client client;
    private WebTarget tut;
    static final String ADDITION_URI = "http://localhost:8383/addition/resources/timeouts";

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(ADDITION_URI);
    }

    @Test
    public void increaseCounterOnTimeout() {
        JsonObject response = this.tut.
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        int initial = numberOfTimeouts(response);

        causeTimeout();

        response = this.tut.
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        int afterTimeout = numberOfTimeouts(response);

        assertThat(afterTimeout, is(initial + 1));

        numberOfTimeouts(response);

    }

    public void causeTimeout() {
        AdditionResourceIT additionTest = new AdditionResourceIT();
        additionTest.init();
        additionTest.timeoutWith42();
    }

    static int numberOfTimeouts(JsonObject input) {
        assertNotNull(input);
        return input.getInt("addition-timeouts");
    }
}
