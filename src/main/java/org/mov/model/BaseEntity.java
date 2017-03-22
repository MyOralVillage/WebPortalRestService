package org.mov.model;

import java.util.Date;

public class BaseEntity {
    protected Long id;
    protected Date dateCreated;
    protected Date dateUpdated;

    public Long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
