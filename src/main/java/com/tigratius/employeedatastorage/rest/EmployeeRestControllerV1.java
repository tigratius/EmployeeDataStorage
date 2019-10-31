package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.dto.EmployeeDto;
import com.tigratius.employeedatastorage.model.Employee;
import com.tigratius.employeedatastorage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeRestControllerV1 {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestControllerV1(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Employee employee = this.employeeService.getById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(EmployeeDto.fromEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {

        List<Employee> employees = this.employeeService.list();

        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(EmployeeDto.toEmployeeDtos(employees));
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {

        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }

        this.employeeService.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {

        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }

        this.employeeService.update(id, employee);

        return ResponseEntity.accepted().body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        Employee employee = this.employeeService.getById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        this.employeeService.delete(id);

        return ResponseEntity.accepted().build();
    }


}
