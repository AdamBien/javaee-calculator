
package com.airhacks.calculator.operations.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Stateless
@Path("operations")
public class OperationsResource {

    @Inject
    OperationService operations;

    @POST
    @Path("addition")
    public JsonObject addition(JsonObject input) {
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        int result = operations.add(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();
    }


}
