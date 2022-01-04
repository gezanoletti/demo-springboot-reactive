package com.gezanoletti.demospringbootreactive.people;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
