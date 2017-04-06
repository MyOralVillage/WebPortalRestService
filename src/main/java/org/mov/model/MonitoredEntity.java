package org.mov.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
public abstract class MonitoredEntity extends BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", nullable = false)
    protected Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATED", nullable = false)
    protected Date dateUpdated;

    @ManyToOne
    @JoinColumn(name = "USER_CREATED_ID", nullable = false)
    protected User userCreated;

    @ManyToOne
    @JoinColumn(name = "USER_UPDATED_ID", nullable = false)
    protected User userUpdated;

    public MonitoredEntity() {
        this.dateCreated = Date.from(Instant.now());
        this.dateUpdated = Date.from(Instant.now());
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

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(User userUpdated) {
        this.userUpdated = userUpdated;
    }
}
