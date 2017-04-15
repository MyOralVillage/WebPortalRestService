package org.mov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Tag extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return name != null ? name.equals(tag.name) : tag.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
