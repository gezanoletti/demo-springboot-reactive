package com.gezanoletti.demospringbootreactive.people;

import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class EmployeeCreateDto
{
    @NotBlank
    String lastName;

    @NotBlank
    String firstName;
}
