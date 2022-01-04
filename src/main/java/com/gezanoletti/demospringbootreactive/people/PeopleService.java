package com.gezanoletti.demospringbootreactive.people;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PeopleService
{
    Flux<Employee> findAllEmployees();

    Mono<Employee> findEmployeeById(int employeeId);

    Flux<Employee> findEmployeesByIds(List<Integer> ids);

    Mono<Employee> createEmployee(EmployeeCreateDto employeeCreateDto);

    Mono<Employee> updateEmployee(int employeeId, EmployeeUpdateDto employeeUpdateDto);

    Mono<Void> deleteEmployee(int employeeId);
}
