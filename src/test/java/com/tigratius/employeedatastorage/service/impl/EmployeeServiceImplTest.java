package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.DepartmentBuilder;
import com.tigratius.employeedatastorage.builder.EmployeeBuilder;
import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.repository.DepartmentRepository;
import com.tigratius.employeedatastorage.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tigratius.employeedatastorage.builder.CommonBuilder.*;
import static com.tigratius.employeedatastorage.builder.EmployeeBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void saveNewEmployee() {

        Department department1 = DepartmentBuilder.departmentDb("d1").build();
        Department department2 = DepartmentBuilder.departmentDb("d2").id(id("2")).build();

        Employee employee = employeeWithDepartments("Alex", "Ivanov").build();

        when(departmentRepository.findByName("d1")).thenReturn(department1);
        when(departmentRepository.findByName("d2")).thenReturn(department2);

        employeeService.save(employee);

        Assert.assertEquals(employee.getStatus(), Status.ACTIVE);
        Assert.assertNotNull(employee.getCreated());
        Assert.assertNotNull(employee.getUpdated());

        verify(employeeRepository, times(1)).save(employee);

        for (Department department:
             employee.getDepartments()) {
            verify(departmentRepository, times(1)).findByName(department.getName());
        }
    }

    @Test
    public void update() {
        Employee employee = employeeDb("Alex", "Ivanov").build();
        Date timeUpdatedBefore = employee.getUpdated();

        Department department1 = DepartmentBuilder.departmentDb("d1").build();

        Employee updatedEmployee = employee("Petr", "Petrov")
                                    .salary(number("800"))
                                    .birthDate(date("1986-11-11"))
                                    .employmentDate(date("2018-11-11"))
                                    .status(Status.NOT_ACTIVE)
                                    .departments(CommonBuilder.list(department1))
                                    .build();

        when(departmentRepository.findByName("d1")).thenReturn(department1);
        when(employeeRepository.getOne(id("1"))).thenReturn(employee);

        employeeService.update(employee.getId(), updatedEmployee);

        Assert.assertEquals(updatedEmployee.getFirstName(), employee.getFirstName());
        Assert.assertEquals(updatedEmployee.getLastName(), employee.getLastName());
        Assert.assertEquals(updatedEmployee.getSalary(), employee.getSalary());
        Assert.assertEquals(updatedEmployee.getBirthDate(), employee.getBirthDate());
        Assert.assertEquals(updatedEmployee.getEmploymentDate(), employee.getEmploymentDate());
        Assert.assertEquals(updatedEmployee.getDepartments().size(), employee.getDepartments().size());
        Assert.assertEquals(Status.NOT_ACTIVE, employee.getStatus());
        Assert.assertNotEquals(timeUpdatedBefore, employee.getUpdated());

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void delete() {

        Employee employee = employeeDb("Alex", "Ivanov").build();
        Date timeUpdatedBefore = employee.getUpdated();
        Date timeCreatedBefore = employee.getCreated();

        when(employeeRepository.getOne(id("1"))).thenReturn(employee);

        employeeService.delete(employee.getId());

        Assert.assertEquals(employee.getStatus(), Status.DELETED);
        Assert.assertNotEquals(timeUpdatedBefore, employee.getUpdated());
        Assert.assertEquals(timeCreatedBefore, employee.getCreated());

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void getById() {

        Employee employee = employeeDb("Alex", "Ivanov").build();

        when(employeeRepository.getOne(id("1"))).thenReturn(employee);

        employeeService.getById(employee.getId());

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, employee.getId());

        verify(employeeRepository, times(1)).getOne(expectedId);
    }

    @Test
    public void list() {

        Employee employee1 = employeeDb("Alex", "Ivanov").build();
        Employee employee2 = employeeDb("Petr", "Petrov").id(id("2")).build();
        List<Employee> employees = CommonBuilder.list(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> actualResult = employeeService.list();

        Assert.assertEquals(employees.size(), actualResult.size());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void updateFirstAndLastName()
    {
        Employee employee = employeeDb("Alex", "Ivanov").build();

        Employee employeeUpdatedFistName = employee("AlexUpdated", null)
                                            .birthDate(null)
                                            .employmentDate(null)
                                            .salary(null)
                                            .status(null)
                                            .build();

        Employee employeeUpdatedLastName = employee(null, "IvanovUpdated")
                                            .birthDate(null)
                                            .employmentDate(null)
                                            .salary(null)
                                            .status(null)
                                            .build();

        when(employeeRepository.getOne(id("1"))).thenReturn(employee);

        employeeService.update(employee.getId(), employeeUpdatedFistName);

        Assert.assertEquals(employeeUpdatedFistName.getFirstName(), employee.getFirstName());

        employeeService.update(employee.getId(), employeeUpdatedLastName);

        Assert.assertEquals(employeeUpdatedLastName.getLastName(), employee.getLastName());

    }
}