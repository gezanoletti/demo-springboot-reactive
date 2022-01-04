package com.gezanoletti.demospringbootreactive.people;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(EmployeeController.class)
class EmployeeControllerTest
{
    static final String GET_EMPLOYEES_ENDPOINT = "/v1/employees/";
    static final String GET_EMPLOYEE_ENDPOINT = "/v1/employees/{id}";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    PeopleService peopleService;

    EasyRandom generator = new EasyRandom();


    @Test
    void getAllEmployees()
    {
        final var employees = generator.objects(Employee.class, 5);
        when(peopleService.findAllEmployees()).thenReturn(Flux.fromStream(employees));

        webTestClient
            .get()
            .uri(GET_EMPLOYEES_ENDPOINT)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(EmployeeDto.class)
            .hasSize(5)
            .value(employeeDtos -> employeeDtos.forEach(Assertions::assertNotNull));

        verify(peopleService).findAllEmployees();
    }


    @Test
    void getAllEmployeesAssertBody()
    {
        final var employees = generator.objects(Employee.class, 5);
        when(peopleService.findAllEmployees()).thenReturn(Flux.fromStream(employees));

        webTestClient
            .get()
            .uri(GET_EMPLOYEES_ENDPOINT)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$.size()").isEqualTo(5)
            .jsonPath("$[0].id").isNotEmpty()
            .jsonPath("$[0].firstName").isNotEmpty()
            .jsonPath("$[0].lastName").isNotEmpty();

        verify(peopleService).findAllEmployees();
    }


    @Test
    void getEmployeeById()
    {
        final var employee = generator.nextObject(Employee.class);
        when(peopleService.findEmployeeById(1)).thenReturn(Mono.just(employee));

        webTestClient
            .get()
            .uri(GET_EMPLOYEE_ENDPOINT, 1)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.firstName").isNotEmpty()
            .jsonPath("$.lastName").isNotEmpty();

        verify(peopleService).findEmployeeById(1);
    }
}
