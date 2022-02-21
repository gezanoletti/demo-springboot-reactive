package com.gezanoletti.demospringbootreactive.people;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import static com.gezanoletti.demospringbootreactive.setup.RabbitConfig.EMPLOYEE_CREATE_ROUTING_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventSender
{
    private final ObjectMapper objectMapper;
    private final Sender sender;


    public void sendOnCreateEmployee(final EmployeeCreateEvent employeeCreateEvent)
    {
        try
        {
            final byte[] payload = objectMapper.writeValueAsBytes(employeeCreateEvent);
            final var message = new OutboundMessage("", EMPLOYEE_CREATE_ROUTING_KEY, payload);

            sender.sendWithPublishConfirms(Mono.just(message))
                .doOnError(throwable -> log.error("Error occurred while sending message to RabbitMQ", throwable))
                .subscribe(outboundMessageResult -> {
                    if (outboundMessageResult.isAck())
                    {
                        log.info("Event sent for employee {}", employeeCreateEvent.getId());
                    }
                });
        }
        catch (JsonProcessingException e)
        {
            log.error("error occurred while processing payload", e);
        }
    }
}
