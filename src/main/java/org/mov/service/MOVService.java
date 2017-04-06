package org.mov.service;

import org.mov.entity.*;

import java.util.Collection;

public interface MOVService {
    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    void saveDocument(Document document);

    void removeDocument(Document document);

    Document findDocumentById(Long id);

    Collection<Document> findAllDocuments();

    void saveTheme(Theme theme);

    void removeTheme(Theme theme);

    Theme findThemeById(Long id);

    Theme findThemeByName(String name);

    Collection<Theme> findAllThemes();

    void saveCountry(Country country);

    void removeCountry(Country country);

    Country findCountryById(Long id);

    Country findCountryByName(String name);

    Collection<Country> findAllCountries();

    void saveDocumentType(DocumentType documentType);

    void removeDocumentType(DocumentType documentType);

    DocumentType findDocumentTypeById(Long id);

    DocumentType findDocumentTypeByName(String name);

    Collection<DocumentType> findAllDocumentTypes();

    void saveTag(Tag tag);

    void removeTag(Tag tag);

    Tag findTagById(Long id);

    Tag findTagByName(String name);

    Collection<Tag> findAllTags();
}
