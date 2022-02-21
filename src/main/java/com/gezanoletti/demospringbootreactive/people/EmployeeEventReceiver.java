package com.gezanoletti.demospringbootreactive.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.gezanoletti.demospringbootreactive.setup.RabbitConfig.EMPLOYEE_CREATE_ROUTING_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventReceiver
{
    @RabbitListener(queues = EMPLOYEE_CREATE_ROUTING_KEY)
    public void receive(final String input)
    {
        log.info("Event received {}", input);
    }
}
