package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.repository.DepartmentRepository;
import com.tigratius.employeedatastorage.repository.EmployeeRepository;
import com.tigratius.employeedatastorage.service.EmployeeService;
import com.tigratius.employeedatastorage.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void save(Employee employee) {

        if (employee.getStatus() == null)
        {
            employee.setStatus(Status.ACTIVE);
        }

        employee.setCreated(DateUtil.getDateNow());
        employee.setUpdated(DateUtil.getDateNow());

        List<Department> employeeDepartments = new ArrayList<>();

        for (Department department : employee.getDepartments()
        ) {
            Department departmentEmployee = departmentRepository.findByName(department.getName());
            employeeDepartments.add(departmentEmployee);
        }

        employee.setDepartments(employeeDepartments);
        employeeRepository.save(employee);
    }

    @Override
    public void update(Long id, Employee updatedEmployee) {

        Employee employee = getById(id);

        if (updatedEmployee.getFirstName() != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
        }

        if (updatedEmployee.getLastName() != null) {
            employee.setLastName(updatedEmployee.getLastName());
        }

        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(updatedEmployee.getSalary());
        }

        if (updatedEmployee.getBirthDate() != null) {
            employee.setBirthDate(updatedEmployee.getBirthDate());
        }

        if (updatedEmployee.getEmploymentDate() != null) {
            employee.setEmploymentDate(updatedEmployee.getEmploymentDate());
        }

        if (updatedEmployee.getStatus() != null) {
            employee.setStatus(updatedEmployee.getStatus());
        }

        if (updatedEmployee.getDepartments() != null) {
            List<Department> employeeDepartments = new ArrayList<>();
            for (Department department : updatedEmployee.getDepartments()
            ) {
                Department departmentEmployee = departmentRepository.findByName(department.getName());
                employeeDepartments.add(departmentEmployee);
            }

            employee.setDepartments(employeeDepartments);
        }

        employee.setUpdated(DateUtil.getDateNow());
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {

        Employee employee = getById(id);

        employee.setStatus(Status.DELETED);
        employee.setUpdated(DateUtil.getDateNow());

        employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.getOne(id);
    }

    @Override
    public List<Employee> list() {

        return employeeRepository.findAll();
    }
}
