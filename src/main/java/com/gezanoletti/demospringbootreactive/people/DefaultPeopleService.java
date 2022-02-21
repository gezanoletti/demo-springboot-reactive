package com.gezanoletti.demospringbootreactive.people;

import com.gezanoletti.demospringbootreactive.error.NotFoundEntityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPeopleService implements PeopleService
{
    private final EmployeeRepository employeeRepository;
    private final EmployeeEventSender employeeEventSender;


    @Override
    public Flux<Employee> findAllEmployees()
    {
        return employeeRepository.findAll();
    }


    @Override
    public Mono<Employee> findEmployeeById(final int employeeId)
    {
        return employeeRepository.findById(employeeId);
    }


    @Override
    public Flux<Employee> findEmployeesByIds(final List<Integer> ids)
    {
        return employeeRepository.findAllById(ids);
    }


    @Override
    public Mono<Employee> createEmployee(final EmployeeCreateDto employeeCreateDto)
    {
        final var employee = Employee.builder()
            .firstName(employeeCreateDto.getFirstName())
            .lastName(employeeCreateDto.getLastName())
            .build();

        return employeeRepository.save(employee)
            .doOnNext(savedEmployee -> log.info("Employee {} saved successfully", savedEmployee.getId()))
            .doOnNext(savedEmployee -> employeeEventSender.sendOnCreateEmployee(EmployeeMapper.mapToEvent(savedEmployee)));
    }


    @Override
    public Mono<Employee> updateEmployee(final int employeeId, final EmployeeUpdateDto employeeUpdateDto)
    {
        return employeeRepository.findById(employeeId)
            .switchIfEmpty(Mono.error(new NotFoundEntityException()))
            .map(employee -> {
                employee.setFirstName(employeeUpdateDto.getFirstName());
                employee.setLastName(employeeUpdateDto.getLastName());
                return employee;
            })
            .flatMap(employeeRepository::save)
            .doOnNext(savedEmployee -> log.info("Employee {} updated successfully", savedEmployee.getId()));
    }


    @Override
    public Mono<Void> deleteEmployee(final int employeeId)
    {
        return employeeRepository.findById(employeeId)
            .switchIfEmpty(Mono.error(new NotFoundEntityException()))
            .flatMap(employeeRepository::delete)
            .doOnNext(unused -> log.info("Employee {} deleted successfully", employeeId));
    }
}
