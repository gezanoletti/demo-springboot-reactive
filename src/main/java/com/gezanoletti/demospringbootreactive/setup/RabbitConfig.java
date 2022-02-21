package com.gezanoletti.demospringbootreactive.setup;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig
{
    public static final String EMPLOYEE_CREATE_ROUTING_KEY = "employee_create";

    @Autowired
    private final AmqpAdmin amqpAdmin;


    @Bean
    public Mono<Connection> connectionMono(final CachingConnectionFactory cachingConnectionFactory)
    {
        return Mono.fromCallable(() -> cachingConnectionFactory.getRabbitConnectionFactory().newConnection());
    }


    @Bean
    public Sender sender(final Mono<Connection> connectionMono)
    {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }


    @Bean
    public Receiver receiver(final Mono<Connection> connectionMono)
    {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }


    @Bean
    public Flux<Delivery> deliveryFlux(final Receiver receiver)
    {
        return receiver.consumeAutoAck(EMPLOYEE_CREATE_ROUTING_KEY);
    }


    @PostConstruct
    public void init()
    {
        amqpAdmin.declareQueue(new Queue(EMPLOYEE_CREATE_ROUTING_KEY, false, false, true));
    }
}
