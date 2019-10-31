package com.tigratius.employeedatastorage.service;

import com.tigratius.employeedatastorage.model.Department;

public interface DepartmentService extends BaseService<Department>{

    Department findByName(String name);
}
