package com.airhacks.ping.boundary;

import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 *
 * @author airhacks.com
 */
@Stateless
@Path("ping")
public class PingResource {

    @Inject
    Pings ping;

    @GET
    public void ping(@Suspended AsyncResponse response) {
        response.setTimeout(1, TimeUnit.NANOSECONDS);
        System.out.println(".");
        response.resume("Java EE 8 is crazy fast!");
    }

    @GET
    @Path("heavy/{blocking}-{echo}")
    public String heavyPing(@PathParam("blocking") long blocking, @PathParam("echo") String echo) {
        return this.ping.echo(blocking, echo);
    }
}
