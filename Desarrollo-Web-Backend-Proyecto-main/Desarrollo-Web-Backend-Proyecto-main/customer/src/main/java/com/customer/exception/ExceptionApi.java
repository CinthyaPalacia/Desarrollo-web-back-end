package com.customer.exception;

import org.springframework.http.HttpStatus;

public class ExceptionApi extends RuntimeException
{

    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public ExceptionApi(HttpStatus status, String message)
    {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus()
    {
        return status;
    }

    public void setStatus(HttpStatus status)
    {
        this.status = status;
    }
}
