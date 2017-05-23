
package com.airhacks.calculator.breaker.boundary;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@ApplicationException(rollback = true)
public class UnstableExternalServiceException extends WebApplicationException {

    public UnstableExternalServiceException(String message) {
        super(Response.status(Response.Status.SERVICE_UNAVAILABLE).
                header("info", message).
                build());
    }
}
