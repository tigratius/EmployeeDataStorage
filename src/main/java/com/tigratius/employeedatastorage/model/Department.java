package com.tigratius.employeedatastorage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "departments")
@ToString(exclude = {"employees"})
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department extends NamedEntity{

    @JsonIgnore
    @ManyToMany(mappedBy = "departments")
    private List<Employee> employees;
}
