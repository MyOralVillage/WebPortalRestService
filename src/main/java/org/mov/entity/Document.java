package org.mov.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Document extends MonitoredEntity {
    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "FILE_EXTENSION", nullable = false)
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name = "TYPE_ID", nullable = false)
    private DocumentType type;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "THEME_ID", nullable = false)
    private Theme theme;

    @ManyToMany
    @JoinTable(name = "DocumentTag",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID", nullable = false))
    private Set<Tag> tags;

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

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
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
