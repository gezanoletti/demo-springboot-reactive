package com.gezanoletti.demospringbootreactive.people;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.util.Locale;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
public class EmployeeControllerIntegrationTest
{
    static final String EMPLOYEES_ENDPOINT = "/v1/employees/";
    static final String EMPLOYEE_ID_ENDPOINT = "/v1/employees/{id}";
    static final String EMPLOYEES_FIND_BY_ID_ENDPOINT = "/v1/employees/findByIds";

    @Autowired
    ApplicationContext context;

    @Autowired
    EmployeeRepository employeeRepository;

    WebTestClient webTestClient;

    FakeValuesService fakeValuesService = new FakeValuesService(Locale.US, new RandomService());


    @BeforeEach
    void setup()
    {
        webTestClient = WebTestClient
            .bindToApplicationContext(context)
            .build();
    }


    @Test
    void createEmployee()
    {
        final var employeeCreateDto = new EmployeeCreateDto(
            fakeValuesService.letterify("?????"),
            fakeValuesService.letterify("?????")
        );

        webTestClient
            .post()
            .uri(EMPLOYEES_ENDPOINT)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(employeeCreateDto))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.firstName").isNotEmpty()
            .jsonPath("$.lastName").isNotEmpty();
    }


    @Test
    void return400whenCreateEmployee()
    {
        final var employeeCreateDto = new EmployeeCreateDto(
            fakeValuesService.letterify("?????"),
            null
        );

        webTestClient
            .post()
            .uri(EMPLOYEES_ENDPOINT)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(employeeCreateDto))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
    }


    @Test
    void updateEmployee()
    {
        final var employeeId = 1;
        final var employeeUpdateDto = new EmployeeUpdateDto(
            fakeValuesService.letterify("?????"),
            fakeValuesService.letterify("?????")
        );

        webTestClient
            .put()
            .uri(EMPLOYEE_ID_ENDPOINT, employeeId)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(employeeUpdateDto))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.firstName").isNotEmpty()
            .jsonPath("$.lastName").isNotEmpty();
    }


    @Test
    void return404WhenUpdateEmployee()
    {
        final var employeeId = 404;
        final var employeeUpdateDto = new EmployeeUpdateDto(
            fakeValuesService.letterify("?????"),
            fakeValuesService.letterify("?????")
        );

        webTestClient
            .put()
            .uri(EMPLOYEE_ID_ENDPOINT, employeeId)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(employeeUpdateDto))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }


    @Test
    void deleteEmployee()
    {
        final var employee = Employee.builder()
            .firstName(fakeValuesService.letterify("?????"))
            .lastName(fakeValuesService.letterify("?????"))
            .build();
        final var employeeSaved = employeeRepository.save(employee).block();

        assertNotNull(employeeSaved);
        webTestClient
            .delete()
            .uri(EMPLOYEE_ID_ENDPOINT, employeeSaved.getId())
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }


    @Test
    void return404WhenDeleteEmployee()
    {
        final var employeeId = 404;

        webTestClient
            .delete()
            .uri(EMPLOYEE_ID_ENDPOINT, employeeId)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }


    @Test
    void findEmployeesByListIds()
    {
        final var ids = Lists.list(1, 2, 3, 4, 404);

        webTestClient
            .post()
            .uri(EMPLOYEES_FIND_BY_ID_ENDPOINT)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(ids))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(EmployeeDto.class)
            .hasSize(4)
            .value(employeeDtos -> employeeDtos.forEach(Assertions::assertNotNull));
    }
}
