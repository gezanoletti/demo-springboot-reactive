package com.gezanoletti.demospringbootreactive.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundEntityException extends ResponseStatusException
{
    public NotFoundEntityException()
    {
        super(HttpStatus.NOT_FOUND);
    }


    public NotFoundEntityException(final String reason)
    {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
