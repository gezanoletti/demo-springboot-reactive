package com.gezanoletti.demospringbootreactive.people;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("employees")
public class Employee
{
    @Id
    @Column("employee_id")
    private Integer id;

    @Column("last_name")
    private String lastName;

    @Column("first_name")
    private String firstName;

    private String title;

    @Column("title_of_courtesy")
    private String titleOfCourtesy;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("hire_date")
    private LocalDate hireDate;

    private String address;

    private String city;

    private String region;

    @Column("postal_code")
    private String postalCode;

    private String country;

    @Column("home_phone")
    private String homePhone;

    private String extension;

    private byte[] photo;

    private String notes;

    @Column("reports_to")
    private Integer reportsTo;
}
