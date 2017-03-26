package org.mov.service;

import org.mov.model.Country;
import org.mov.model.Document;
import org.mov.model.Theme;
import org.mov.model.User;

import java.util.Collection;

public interface MOVService {
    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    void saveDocument(Document document);

    void removeDocument(Document document);

    Document findDocumentById(Long id);

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
}
