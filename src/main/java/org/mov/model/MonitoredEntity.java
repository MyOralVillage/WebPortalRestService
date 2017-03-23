package org.mov.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class MonitoredEntity extends BaseEntity {
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    protected Date dateCreated;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    protected Date dateUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CREATED_ID", nullable = false)
    protected User userCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UPDATED_ID", nullable = false)
    protected User userUpdated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
