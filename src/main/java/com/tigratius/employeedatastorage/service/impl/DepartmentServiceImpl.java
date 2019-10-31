package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.repository.DepartmentRepository;
import com.tigratius.employeedatastorage.service.DepartmentService;
import com.tigratius.employeedatastorage.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void save(Department department) {

        if (department.getStatus() == null) {
            department.setStatus(Status.ACTIVE);
        }

        department.setCreated(DateUtil.getDateNow());
        department.setUpdated(DateUtil.getDateNow());
        departmentRepository.save(department);
    }

    @Override
    public void update(Long id, Department updatedDepartment) {

        Department department = getById(id);

        if (updatedDepartment.getName() != null) {
            department.setName(updatedDepartment.getName());
        }

        if (updatedDepartment.getStatus() != null) {
            department.setStatus(updatedDepartment.getStatus());
        }

        department.setUpdated(DateUtil.getDateNow());

        departmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {

        Department department = getById(id);

        department.setUpdated(DateUtil.getDateNow());
        department.setStatus(Status.DELETED);

        departmentRepository.save(department);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.getOne(id);
    }

    @Override
    public List<Department> list() {

//        return departmentRepository.findAll();
        return departmentRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Department findByName(String name) {

        return departmentRepository.findByName(name);
    }
}
