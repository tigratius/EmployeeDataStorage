package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto {

    private Long id;
    private String name;

    public Department toDepartment()
    {
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        return department;
    }

    public static DepartmentDto fromDepartment(Department department)
    {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }

    public static List<DepartmentDto> toDepartmentDtos(List<Department> departments)
    {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        for (Department department:departments) {

            departmentDtoList.add(fromDepartment(department));
        }
        return departmentDtoList;
    }
}
