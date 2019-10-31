package com.tigratius.employeedatastorage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@ToString(exclude = {"users"})
@Data
public class Role extends NamedEntity {

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
