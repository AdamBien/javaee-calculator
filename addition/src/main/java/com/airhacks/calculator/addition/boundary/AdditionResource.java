
package com.airhacks.calculator.addition.boundary;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 *
 * @author airhacks.com
 */
@Path("addition")
public class AdditionResource {

    @Inject
    Addition addition;

    @POST
    public void addition(@Suspended AsyncResponse response, JsonObject input) {
        response.setTimeout(1, TimeUnit.SECONDS);
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        int result = addition.add(a, b);
        JsonObject payload = Json.createObjectBuilder().
                add("result", result).
                build();
        response.resume(payload);
    }

}
