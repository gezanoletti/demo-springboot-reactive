package com.gezanoletti.demospringbootreactive.people;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmployeeRepositoryIntegrationTest
{
    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void getById()
    {
        final var mono = employeeRepository.findById(1);

        StepVerifier.create(mono).expectNextCount(1).verifyComplete();

        final var employee = mono.block();
        assertNotNull(employee);
    }


    @Test
    void getByIdNotFound()
    {
        final var mono = employeeRepository.findById(404);

        StepVerifier.create(mono).expectNextCount(0).verifyComplete();

        final var employee = mono.block();
        assertNull(employee);
    }


    @Test
    void getByIdMap()
    {
        final var mono = employeeRepository.findById(1);

        StepVerifier.create(mono).expectNextCount(1).verifyComplete();

        mono.doOnNext(System.out::println)
            .map(Employee::getFirstName)
            .subscribe(Assertions::assertNotNull);
    }


    @Test
    void testFindAllBlockFirst()
    {
        final var flux = employeeRepository.findAll();
        assertNotNull(flux.blockFirst());
    }


    @Test
    void testFindAll()
    {
        final var flux = employeeRepository.findAll();
        flux.doOnNext(System.out::println)
            .map(Employee::getFirstName)
            .subscribe(Assertions::assertNotNull);
    }


    @Test
    void testFindAllToListOfMono()
    {
        employeeRepository.findAll()
            .collectList()
            .subscribe(employee -> employee.forEach(Assertions::assertNotNull));
    }


    @Test
    void testFindAllBlocking()
    {
        final var employees = employeeRepository.findAll()
            .collectList()
            .block();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }


    @Test
    void testFindAllFiltering()
    {
        final var flux = employeeRepository.findAll();
        flux.filter(employee -> "Mr.".equals(employee.getTitleOfCourtesy()))
            .subscribe(Assertions::assertNotNull);
    }


    @Test
    void testFindAllSingle()
    {
        final var flux = employeeRepository.findAll();
        flux.filter(employee -> "Mrs.".equals(employee.getTitleOfCourtesy()))
            .single()
            .subscribe(Assertions::assertNotNull);
    }


    @Test
    void testFindAllNotSingle()
    {
        employeeRepository.findAll()
            .filter(employee -> "INVALID".equals(employee.getTitleOfCourtesy()))
            .single()
            //            .doOnError(throwable -> System.out.println(throwable.getMessage()))
            .onErrorResume(throwable -> Mono.empty())
            .subscribe(Assertions::assertNull);
    }
}
