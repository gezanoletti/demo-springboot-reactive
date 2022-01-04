package com.gezanoletti.demospringbootreactive.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler
{
    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final Throwable throwable)
    {
        if (throwable instanceof ResponseStatusException)
        {
            final var ex = (ResponseStatusException) throwable;
            exchange.getResponse().setStatusCode(ex.getStatus());
        }
        else
        {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return exchange.getResponse().writeWith(Mono.empty());
    }
}
