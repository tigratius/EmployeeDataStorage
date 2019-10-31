package com.tigratius.employeedatastorage.repository;

import com.tigratius.employeedatastorage.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name);

    List<Department> findAllByOrderByIdAsc();
}
