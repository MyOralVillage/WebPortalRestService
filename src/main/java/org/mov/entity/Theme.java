package org.mov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Theme extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public Theme() {
    }

    public Theme(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
