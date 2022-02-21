package com.gezanoletti.demospringbootreactive.people;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeCreateEvent
{
    Integer id;
    String lastName;
    String firstName;
}
