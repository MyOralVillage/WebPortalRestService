package org.mov.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Country extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
