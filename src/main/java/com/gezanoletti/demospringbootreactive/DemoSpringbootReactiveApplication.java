package com.gezanoletti.demospringbootreactive;

import com.gezanoletti.demospringbootreactive.people.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoSpringbootReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringbootReactiveApplication.class, args);
	}

}

@Component
class Demo implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println(employeeRepository.count().block());
	}
}