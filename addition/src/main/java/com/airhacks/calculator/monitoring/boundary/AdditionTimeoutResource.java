
package com.airhacks.calculator.monitoring.boundary;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("timeouts")
@ApplicationScoped
public class AdditionTimeoutResource {

    private AtomicInteger timeoutCounter;

    @PostConstruct
    public void init() {
        this.timeoutCounter = new AtomicInteger();
    }

    @GET
    public JsonObject timeouts() {
        return Json.createObjectBuilder().
                add("addition-timeouts", this.timeoutCounter.intValue()).
                build();
    }

    public void onNewTimeout(@Observes String timeout) {
        this.timeoutCounter.incrementAndGet();
    }
}
