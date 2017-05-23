
package com.airhacks.calculator.configuration.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("configuration")
public class ConfigurationResource {

    @GET
    public JsonObject all() {
        JsonObjectBuilder result = Json.createObjectBuilder();
        System.getenv().
                entrySet().
                forEach(e -> result.add(e.getKey(), e.getValue()));
        return result.build();
    }

}
