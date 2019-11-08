package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.EmployeeBuilder;
import com.tigratius.employeedatastorage.dto.DepartmentDto;
import com.tigratius.employeedatastorage.dto.EmployeeDto;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRestControllerV1Test {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeRestControllerV1 employeeRestController;

    @Test
    public void get() {

        Employee employee = EmployeeBuilder.employeeDb("firstName", "lastName").build();

        when(employeeService.getById(employee.getId())).thenReturn(employee);
        ResponseEntity<EmployeeDto> response = employeeRestController.get(employee.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(employee.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());
        assertEquals(employee.getId(), response.getBody().getId());
    }

    @Test
    public void getFail() {

        ResponseEntity<EmployeeDto> response1 = employeeRestController.get(null);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Long id = CommonBuilder.id("1");
        when(employeeService.getById(id)).thenReturn(null);
        ResponseEntity<EmployeeDto> response2 = employeeRestController.get(id);

        assertEquals( HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void getAll() {
        Employee employee1 = EmployeeBuilder.employeeDb("firstName1", "lastName1").build();
        Employee employee2 = EmployeeBuilder.employeeDb("firstName2", "lastName2").id(CommonBuilder.id("2")).build();
        List<Employee> employees = CommonBuilder.list(employee1, employee2);

        when(employeeService.list()).thenReturn(employees);

        ResponseEntity<List<EmployeeDto>> response = employeeRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(employee1.getId(), response.getBody().get(0).getId());
        assertEquals(employee2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void getAllFail() {

        when(employeeService.list()).thenReturn(new ArrayList<>());
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {

        Employee employee = EmployeeBuilder.employee("firstName1", "lastName1").build();

        ResponseEntity<Employee> response =  employeeRestController.save(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(employee.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());
        assertEquals(employee.getSalary(), Objects.requireNonNull(response.getBody()).getSalary());

        verify(employeeService, times(1)).save(employee);
    }

    @Test
    public void saveFail() {
        ResponseEntity<Employee> response =  employeeRestController.save(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(employeeService, never()).save(any(Employee.class));
    }

    @Test
    public void update() {
        Employee employee = EmployeeBuilder.employee("firstName2", "lastName2").build();

        Long id  = CommonBuilder.id("1");
        ResponseEntity<Employee> response =  employeeRestController.update(id, employee);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(employee.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(employee.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());

        verify(employeeService, times(1)).update(id, employee);
    }

    @Test
    public void updateFail() {
        ResponseEntity<Employee> response =  employeeRestController.update(null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(employeeService, never()).update(anyLong(), any(Employee.class));
    }

    @Test
    public void delete() {

        Employee employee = EmployeeBuilder.employeeDb("firstName1", "lastName1").build();
        when(employeeService.getById(employee.getId())).thenReturn(employee);

        ResponseEntity response =  employeeRestController.delete(employee.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        verify(employeeService, times(1)).delete(employee.getId());
    }

    @Test
    public void deleteFail() {

        Long id = CommonBuilder.id("1");
        when(employeeService.getById(id)).thenReturn(null);

        ResponseEntity response =  employeeRestController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(employeeService, never()).delete(anyLong());
    }
}