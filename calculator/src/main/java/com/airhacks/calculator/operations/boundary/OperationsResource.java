
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
        int result = this.operations.add(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();
    }

    @POST
    @Path("multiplication")
    public JsonObject multiplication(JsonObject input){
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        int result = this.operations.multiply(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();

    }
    @POST
    @Path("division")
    public JsonObject division(JsonObject input){
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        double result = this.operations.divide(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();

    }

    @POST
    @Path("subtraction")
    public JsonObject subtraction(JsonObject input){
        int a = input.getJsonNumber("a").intValue();
        int b = input.getJsonNumber("b").intValue();
        double result = this.operations.substract(a, b);
        return Json.createObjectBuilder().
                add("result", result).
                build();

    }


}
