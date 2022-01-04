package com.gezanoletti.demospringbootreactive.people;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController
{
    private final PeopleService peopleService;


    @GetMapping
    public Flux<EmployeeDto> getAllEmployees()
    {
        return peopleService.findAllEmployees()
            .map(EmployeeMapper::mapToEmployeeDto);
    }


    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable int id)
    {
        return peopleService.findEmployeeById(id)
            .map(EmployeeMapper::mapToEmployeeDto);
    }


    @PostMapping("/findByIds")
    public Flux<EmployeeDto> getEmployeeByIds(@RequestBody @Validated final List<Integer> ids)
    {
        return peopleService.findEmployeesByIds(ids)
            .map(EmployeeMapper::mapToEmployeeDto);
    }


    @PostMapping
    public Mono<ResponseEntity<EmployeeDto>> createEmployee(@RequestBody @Validated EmployeeCreateDto employeeCreateDto)
    {
        return peopleService.createEmployee(employeeCreateDto)
            .map(EmployeeMapper::mapToEmployeeDto)
            .map(employeeDto -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeDto));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> updateEmployee(
        @PathVariable("id") final int employeeId,
        @RequestBody @Validated final EmployeeUpdateDto employeeUpdateDto)
    {
        return peopleService.updateEmployee(employeeId, employeeUpdateDto)
            .map(EmployeeMapper::mapToEmployeeDto)
            .map(ResponseEntity::ok);
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable("id") final int employeeId)
    {
        return peopleService.deleteEmployee(employeeId)
            .map(ResponseEntity::ok);
    }
}
