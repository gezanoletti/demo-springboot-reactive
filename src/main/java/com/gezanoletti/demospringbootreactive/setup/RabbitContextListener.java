package com.gezanoletti.demospringbootreactive.setup;

import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitContextListener
{
    private final Mono<Connection> connectionMono;


    @PreDestroy
    public void close() throws IOException
    {
        log.info("Shutdown RabbitMQ connection");
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
