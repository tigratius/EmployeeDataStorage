package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;

    private List<DepartmentDto> departments;

    /*public Employee toEmployee(){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setBirthDate(birthDate);
        employee.setEmploymentDate(employmentDate);

        return employee;
    }*/

    public static EmployeeDto fromEmployee(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmploymentDate(employee.getEmploymentDate());
        employeeDto.setBirthDate(employee.getBirthDate());
        employeeDto.setSalary(employee.getSalary());

        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        for (Department department: employee.getDepartments()
        ) {
            departmentDtoList.add(DepartmentDto.fromDepartment(department));
        }

        employeeDto.setDepartments(departmentDtoList);

        return employeeDto;
    }

    public static List<EmployeeDto> toEmployeeDtos(List<Employee> employees){

        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for (Employee employee:employees) {

            employeeDtoList.add(fromEmployee(employee));
        }
        return employeeDtoList;
    }
}
