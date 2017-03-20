
package com.airhacks.calculator.addition.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("addition")
public class AdditionResource {

    @Inject
    Addition addition;

    @POST
    public JsonObject addition(JsonObject input) {
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        int result = addition.add(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();
    }

}
