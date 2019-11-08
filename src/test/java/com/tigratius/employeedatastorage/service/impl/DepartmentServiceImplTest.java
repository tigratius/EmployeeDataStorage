package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.repository.DepartmentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static com.tigratius.employeedatastorage.builder.CommonBuilder.*;
import static com.tigratius.employeedatastorage.builder.DepartmentBuilder.department;
import static com.tigratius.employeedatastorage.builder.DepartmentBuilder.departmentDb;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    public void saveNewDepartment() {

        Department department = department("departmentName").build();

        departmentService.save(department);

        Assert.assertEquals(department.getStatus(), Status.ACTIVE);
        Assert.assertNotNull(department.getCreated());
        Assert.assertNotNull(department.getUpdated());

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void updateDepartment() {

        Department department = departmentDb("departmentName").build();
        Date timeUpdatedBefore = department.getUpdated();

        Department updatedDepartment = department("updatedName").status(Status.NOT_ACTIVE).build();

        when(departmentRepository.getOne(id("1"))).thenReturn(department);

        departmentService.update(department.getId(), updatedDepartment);

        Assert.assertEquals(updatedDepartment.getName(), department.getName());
        Assert.assertEquals(Status.NOT_ACTIVE, department.getStatus());
        Assert.assertNotEquals(timeUpdatedBefore, department.getUpdated());

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void deleteDepartment() {

        Department department = departmentDb("departmentName").build();

        when(departmentRepository.getOne(id("1"))).thenReturn(department);

        Date timeUpdatedBefore = department.getUpdated();
        Date timeCreatedBefore = department.getCreated();

        departmentService.delete(department.getId());

        Assert.assertEquals(department.getStatus(), Status.DELETED);
        Assert.assertNotEquals(timeUpdatedBefore, department.getUpdated());
        Assert.assertEquals(timeCreatedBefore, department.getCreated());

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void getById() {

        Department department = departmentDb("departmentName").build();

        when(departmentRepository.getOne(id("1"))).thenReturn(department);

        departmentService.getById(department.getId());

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, department.getId());

        verify(departmentRepository, times(1)).getOne(expectedId);
    }

    @Test
    public void findAll() {

        Department department = departmentDb("departmentName").build();
        Department departmentOther = departmentDb("departmentOther").id(id("2")).build();
        List<Department> departments = list(department, departmentOther);

        when(departmentRepository.findAllByOrderByIdAsc()).thenReturn(departments);

        List<Department> actualResult = departmentService.list();

        Assert.assertEquals(departments.size(), actualResult.size());

        verify(departmentRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    public void finByName() {

        String departmentName = "departmentName";
        Department department = departmentDb(departmentName).build();

        when(departmentRepository.findByName(departmentName)).thenReturn(department);

        Department actualDepartment = departmentService.findByName(departmentName);

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, actualDepartment.getId());

        verify(departmentRepository, times(1)).findByName(anyString());
    }

}