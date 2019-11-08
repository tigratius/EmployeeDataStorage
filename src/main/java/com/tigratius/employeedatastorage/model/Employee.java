package com.tigratius.employeedatastorage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "employment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "employee_departments", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @OrderBy(value = "id ASC")
    private List<Department> departments;
}
