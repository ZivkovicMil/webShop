package com.webShop.webShop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer role_id;

    @Column(name = "role_name")
    private String role_name;

    @ManyToMany(mappedBy = "list_of_roles", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<User> list_of_users = new HashSet<>();

    public Role() {
    }

    public Role(String role_name) {
        this.role_name = role_name;
    }

    public Role(Integer role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public Integer getId() {
        return role_id;
    }

    public void setId(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
