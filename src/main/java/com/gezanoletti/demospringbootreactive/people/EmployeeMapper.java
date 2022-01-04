package com.gezanoletti.demospringbootreactive.people;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper
{
    public EmployeeDto mapToEmployeeDto(final Employee employee)
    {
        return EmployeeDto.builder()
            .id(employee.getId())
            .firstName(employee.getFirstName())
            .lastName(employee.getLastName())
            .build();
    }


    public Employee mapToEmployee(final EmployeeCreateDto employeeCreateDto)
    {
        return Employee.builder()
            .firstName(employeeCreateDto.getFirstName())
            .lastName(employeeCreateDto.getLastName())
            .build();
    }
}
