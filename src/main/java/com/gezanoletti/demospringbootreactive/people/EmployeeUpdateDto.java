package com.gezanoletti.demospringbootreactive.people;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class EmployeeUpdateDto
{
    @NotBlank
    String lastName;

    @NotBlank
    String firstName;
}
