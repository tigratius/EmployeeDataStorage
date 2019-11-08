package com.tigratius.employeedatastorage.builder;

import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.model.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EmployeeBuilder {

    private Employee employee = new Employee();

    public static EmployeeBuilder employee(String firstName, String lastName) {
        return new EmployeeBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .salary(CommonBuilder.number("1000"))
                .birthDate(CommonBuilder.date("1990-10-12"))
                .employmentDate(CommonBuilder.date("2012-01-12"));
    }

    public static EmployeeBuilder employeeDb(String firstName, String lastName) {
        return employee(firstName, lastName)
                .id(CommonBuilder.id("1"))
                .departments(CommonBuilder.list(
                        DepartmentBuilder.departmentDb("d1").build(),
                        DepartmentBuilder.departmentDb("d2").id(CommonBuilder.id("2")).build()))
                .status(Status.ACTIVE)
                .createdDate(new Date())
                .updatedDate(new Date());
    }

    public static EmployeeBuilder employeeWithDepartments(String firstName, String lastName) {
        return employee(firstName, lastName)
                .departments(CommonBuilder.list(
                        DepartmentBuilder.department("d1").build(),
                        DepartmentBuilder.department("d2").build()));
    }

    public EmployeeBuilder id(Long id)
    {
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder firstName(String firstName) {
        employee.setFirstName(firstName);
        return this;
    }

    public EmployeeBuilder lastName(String lastName) {
        employee.setLastName(lastName);
        return this;
    }

    public EmployeeBuilder salary(BigDecimal salary) {
        employee.setSalary(salary);
        return this;
    }

    public EmployeeBuilder birthDate(Date birthDate) {
        employee.setBirthDate(birthDate);
        return this;
    }

    public EmployeeBuilder employmentDate(Date employmentDate) {
        employee.setEmploymentDate(employmentDate);
        return this;
    }

    public EmployeeBuilder status(Status status) {
        employee.setStatus(status);
        return this;
    }

    public EmployeeBuilder createdDate(Date date) {
        employee.setCreated(date);
        return this;
    }

    public EmployeeBuilder updatedDate(Date date) {
        employee.setUpdated(date);
        return this;
    }

    public EmployeeBuilder departments(List<Department> departments)
    {
        employee.setDepartments(departments);
        return this;
    }

    public Employee build() {
        return employee;
    }
}
