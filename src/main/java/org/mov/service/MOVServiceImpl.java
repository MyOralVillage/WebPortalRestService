package org.mov.service;

import org.mov.model.Country;
import org.mov.model.Document;
import org.mov.model.Theme;
import org.mov.model.User;
import org.mov.repository.CountryRepository;
import org.mov.repository.DocumentRepository;
import org.mov.repository.ThemeRepository;
import org.mov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class MOVServiceImpl implements MOVService {
    private DocumentRepository documentRepository;
    private UserRepository userRepository;
    private ThemeRepository themeRepository;
    private CountryRepository countryRepository;

    @Autowired
    public MOVServiceImpl(UserRepository userRepository,
                          DocumentRepository documentRepository,
                          ThemeRepository themeRepository,
                          CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.themeRepository = themeRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public void removeUser(User user) {
        userRepository.removeUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveDocument(Document document) {
        documentRepository.saveDocument(document);
    }

    @Override
    public void removeDocument(Document document) {
        documentRepository.removeDocument(document);
    }

    @Override
    public Document findDocumentById(Long id) {
        return documentRepository.findDocumentById(id);
    }

    @Override
    public Collection<Document> findAllDocuments() {
        return documentRepository.findAllDocuments();
    }

    @Override
    public void saveTheme(Theme theme) {
        themeRepository.saveTheme(theme);
    }

    @Override
    public void removeTheme(Theme theme) {
        themeRepository.removeTheme(theme);
    }

    @Override
    public Theme findThemeById(Long id) {
        return themeRepository.findThemeById(id);
    }

    @Override
    public Theme findThemeByName(String name) {
        return themeRepository.findThemeByName(name);
    }

    @Override
    public Collection<Theme> findAllThemes() {
        return themeRepository.findAllThemes();
    }

    @Override
    public void saveCountry(Country country) {
        countryRepository.saveCountry(country);
    }

    @Override
    public void removeCountry(Country country) {
        countryRepository.removeCountry(country);
    }

    @Override
    public Country findCountryById(Long id) {
        return countryRepository.findCountryById(id);
    }

    @Override
    public Country findCountryByName(String name) {
        return countryRepository.findCountryByName(name);
    }

    @Override
    public Collection<Country> findAllCountries() {
        return countryRepository.findAllCountries();
    }
}
