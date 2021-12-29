package com.gezanoletti.demospringbootreactive.people;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Integer>
{
}
