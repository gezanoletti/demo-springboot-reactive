package com.gezanoletti.demospringbootreactive.people;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DefaultPeopleServiceIntegrationTest
{
    @Autowired
    private DefaultPeopleService peopleService;


    @Test
    public void getAllEmployees()
    {
        peopleService.findAllEmployees().subscribe(Assertions::assertNotNull);
    }


    @Test
    public void createEmployee() throws InterruptedException
    {
        var countDownLatch = new CountDownLatch(1);

        peopleService.createEmployee(new EmployeeCreateDto("Skywalker", "Luke"))
            .subscribe(employee -> {
                assertThat(employee.getId()).isNotNull();
                countDownLatch.countDown();
            });

        countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }
}
