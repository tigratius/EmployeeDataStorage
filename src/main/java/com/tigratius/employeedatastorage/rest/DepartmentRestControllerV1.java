package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.dto.DepartmentDto;
import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentRestControllerV1{

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentRestControllerV1(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> get(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Department department = this.departmentService.getById(id);

        if (department == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DepartmentDto.fromDepartment(department));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {

        List<Department> departments = this.departmentService.list();

        if (departments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DepartmentDto.toDepartmentDtos(departments));
    }

    @PostMapping
    public ResponseEntity<Department> save(@RequestBody Department department) {

        if (department == null) {
            return ResponseEntity.badRequest().build();
        }

        this.departmentService.save(department);

        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody Department department) {

        if (id == null || department == null) {
            return ResponseEntity.badRequest().build();
        }

        this.departmentService.update(id, department);

        return ResponseEntity.accepted().body(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> delete(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Department department = this.departmentService.getById(id);

        if (department == null) {
            return ResponseEntity.notFound().build();
        }

        this.departmentService.delete(id);

        return ResponseEntity.accepted().build();
    }
}
