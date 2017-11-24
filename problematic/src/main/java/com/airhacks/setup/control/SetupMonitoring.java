
package com.airhacks.setup.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Singleton
@Startup
public class SetupMonitoring {

    private WebTarget monitoring;

    @Resource
    TimerService ts;
    private Timer timer;


    @PostConstruct
    public void init() {
        this.reconfigure();
    }

    void setupTimer() {
        this.timer = ts.createSingleActionTimer(30 * 1000, new TimerConfig(null, false));

    }

    @Timeout
    public void reconfigure() {
        this.monitoring = ClientBuilder.
                newClient().
                target("http://localhost:8080/firehose/resources/configurations/");
        this.configure("problematic503", "http://localhost:4848/monitoring/domain/server/http-service/server/request.json");
        this.configure("problematicRollbacks", "http://localhost:4848/monitoring/domain/server/transaction-service/rolledbackcount.json");
        this.configure("avgRequestProcessingTime", "http://localhost:4848/monitoring/domain/server/http-service/server/request/processingtime.json");
        this.configure("countqueued1minuteaverage", "http://localhost:4848/monitoring/domain/server/network/connection-queue/countqueued1minuteaverage.json");
        this.configure("echoExecutionTime", "http://localhost:4848/monitoring/domain/server/applications/problematic/Pings/bean-methods/echo-long-java.lang.String.json");
        this.configure("currentThreadsBusy", "http://localhost:4848/monitoring/domain/server/network/http-listener-1/thread-pool/currentthreadsbusy.json");
        this.configure("usedHeapSize", "http://localhost:4848/monitoring/domain/server/jvm/memory/usedheapsize-count.json");
    }

    public void configure(String name, String uri) {
        String extractor = null;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + name + ".js")))) {
            extractor = buffer.lines().collect(Collectors.joining("\n"));
            System.out.println("registering extractor = " + extractor);
        } catch (IOException ex) {
            throw new IllegalStateException("Initialization error", ex);
        }
        JsonObject configuration = Json.createObjectBuilder().
                add("uri", uri).
                add("extractor", extractor).
                build();

        Response response = monitoring.path(name).request().put(Entity.json(configuration));
        System.out.println("Registration status for " + name + " is: " + response.getStatus());
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            System.out.println("Configuration was not successful, retrying later");
            this.setupTimer();
        }

    }



}
