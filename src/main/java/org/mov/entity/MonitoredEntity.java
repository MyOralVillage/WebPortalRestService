package org.mov.entity;

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
    protected Date dateModified;

    @ManyToOne
    @JoinColumn(name = "USER_CREATED_ID", nullable = false)
    protected User postedBy;

    @ManyToOne
    @JoinColumn(name = "USER_UPDATED_ID", nullable = false)
    protected User userUpdated;

    public MonitoredEntity() {
        this.dateCreated = Date.from(Instant.now());
        this.dateModified = Date.from(Instant.now());
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(User userUpdated) {
        this.userUpdated = userUpdated;
    }
}
