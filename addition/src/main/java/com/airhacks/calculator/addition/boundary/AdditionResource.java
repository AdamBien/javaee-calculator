
package com.airhacks.calculator.addition.boundary;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonNumber;
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
@Stateless
@Path("addition")
public class AdditionResource {

    @Inject
    Addition addition;

    @Inject
    Event<String> timeoutEscalations;

    @Resource
    ManagedExecutorService mes;

    @POST
    public void addition(@Suspended AsyncResponse response, JsonObject input) {
        response.setTimeout(500, TimeUnit.MILLISECONDS);
        response.setTimeoutHandler(this::handleTimeout);
        int a = ofNullable(input.getJsonNumber("a")).
                map(JsonNumber::intValue).
                orElseThrow(() -> new LackingParameterException("a"));
        int b = ofNullable(input.getJsonNumber("b")).
                map(JsonNumber::intValue).
                orElseThrow(() -> new LackingParameterException("b"));
        supplyAsync(() -> computeResult(a, b), mes).thenAccept(response::resume);
    }

    JsonObject computeResult(int a, int b) {
        int result = addition.add(a, b);
        JsonObject payload = Json.createObjectBuilder().
                add("result", result).
                build();
        return payload;
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
