package org.mov.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Document extends MonitoredEntity {
    @Column(nullable = false)
    protected String title;
    protected String description;

    @Enumerated(EnumType.STRING)
    protected DocumentType type;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    protected Country country;

    @ManyToOne
    @JoinColumn(name = "THEME_ID")
    protected Theme theme;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "DOCUMENT_TAG",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    @Enumerated(EnumType.STRING)
    protected Set<Tag> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
