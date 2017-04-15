package org.mov.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Document extends MonitoredEntity {
    @Column(name = "TITLE", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "FILE_EXTENSION", nullable = false)
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name = "TYPE_ID", nullable = false)
    private DocumentType category;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "THEME_ID", nullable = false)
    private Theme theme;

    @ManyToMany
    @JoinTable(name = "DocumentSubCategory",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "SUB_CAT_ID", nullable = false))
    private Set<SubCategory> subCategories;

    @ManyToMany
    @JoinTable(name = "DocumentTag",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID", nullable = false))
    private Set<Tag> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public DocumentType getCategory() {
        return category;
    }

    public void setCategory(DocumentType category) {
        this.category = category;
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

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
