package com.tigratius.employeedatastorage.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
}
