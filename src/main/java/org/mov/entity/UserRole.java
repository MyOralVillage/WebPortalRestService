package org.mov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserRole extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }
}
