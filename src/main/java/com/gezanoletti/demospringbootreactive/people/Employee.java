package com.gezanoletti.demospringbootreactive.people;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("employees")
public class Employee
{
    @Id
    private Integer employee_id;

    private String last_name;

    private String first_name;
}
