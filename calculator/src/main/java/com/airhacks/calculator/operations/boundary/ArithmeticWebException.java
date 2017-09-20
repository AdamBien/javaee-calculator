package com.airhacks.calculator.operations.boundary;

import javax.ejb.ApplicationException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException(rollback = false)
public class ArithmeticWebException extends WebApplicationException {
    public ArithmeticWebException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).header("reason",message).build());
    }
}
