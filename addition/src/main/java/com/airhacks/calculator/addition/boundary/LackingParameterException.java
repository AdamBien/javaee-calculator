
package com.airhacks.calculator.addition.boundary;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author airhacks.com
 */
@ApplicationException(rollback = true)
public class LackingParameterException extends WebApplicationException {

    public LackingParameterException(String parameterName) {
        super(Response.status(Status.BAD_REQUEST).
                header("cause", "Parameter: " + parameterName + " is lacking").
                build());
    }
}
