
package com.airhacks.calculator.addition.boundary;

import java.util.concurrent.TimeUnit;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Path("addition")
public class AdditionResource {

    @Inject
    Addition addition;

    @Inject
    Event<String> timeoutEscalations;

    @POST
    public void addition(@Suspended AsyncResponse response, JsonObject input) {
        response.setTimeout(500, TimeUnit.MILLISECONDS);
        response.setTimeoutHandler(this::handleTimeout);
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        int result = addition.add(a, b);
        JsonObject payload = Json.createObjectBuilder().
                add("result", result).
                build();
        response.resume(payload);
    }

    void handleTimeout(AsyncResponse response) {
        timeoutEscalations.fire("addition is too lazy today");
        Response info = Response.
                status(Response.Status.SERVICE_UNAVAILABLE).
                header("reason", "too lazy").
                build();
        response.resume(info);
    }
}
