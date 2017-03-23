package org.mov.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @NotNull
    @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }
}
