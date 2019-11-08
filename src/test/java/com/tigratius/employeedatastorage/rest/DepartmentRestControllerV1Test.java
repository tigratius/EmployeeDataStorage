package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.DepartmentBuilder;
import com.tigratius.employeedatastorage.dto.DepartmentDto;
import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.service.DepartmentService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentRestControllerV1Test {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentRestControllerV1 departmentRestController;

    @Test
    public void get() {

        Department department = DepartmentBuilder.departmentDb("d1").build();
        when(departmentService.getById(department.getId())).thenReturn(department);
        ResponseEntity<DepartmentDto> response = departmentRestController.get(department.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department.getName(), Objects.requireNonNull(response.getBody()).getName());
        assertEquals(department.getId(), response.getBody().getId());
    }

    @Test
    public void getFail() {

        ResponseEntity<DepartmentDto> response1 = departmentRestController.get(null);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Long id = CommonBuilder.id("1");
        when(departmentService.getById(id)).thenReturn(null);
        ResponseEntity<DepartmentDto> response2 = departmentRestController.get(id);

        assertEquals( HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void getAll() {
        Department department1 = DepartmentBuilder.departmentDb("d1").build();
        Department department2 = DepartmentBuilder.departmentDb("d2").id(CommonBuilder.id("2")).build();
        List<Department> departments = CommonBuilder.list(department1, department2);

        when(departmentService.list()).thenReturn(departments);

        ResponseEntity<List<DepartmentDto>> response = departmentRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(department1.getName(), response.getBody().get(0).getName());
        assertEquals(department2.getName(), response.getBody().get(1).getName());
    }

    @Test
    public void getAllFail() {

        when(departmentService.list()).thenReturn(new ArrayList<>());
        ResponseEntity<List<DepartmentDto>> response = departmentRestController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {

        Department department = DepartmentBuilder.department("d1").build();

        ResponseEntity<Department> response =  departmentRestController.save(department);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(department.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void saveFail() {
        ResponseEntity<Department> response =  departmentRestController.save(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void update() {
        Department department = DepartmentBuilder.department("d2").build();

        ResponseEntity<Department> response =  departmentRestController.update(CommonBuilder.id("1"), department);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(department.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void updateFail() {
        ResponseEntity<Department> response =  departmentRestController.update(null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void delete() {

        Department department = DepartmentBuilder.departmentDb("d1").build();
        when(departmentService.getById(department.getId())).thenReturn(department);

        ResponseEntity<Department> response =  departmentRestController.delete(department.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void deleteFail() {

        Long id = CommonBuilder.id("1");
        when(departmentService.getById(id)).thenReturn(null);

        ResponseEntity<Department> response =  departmentRestController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}