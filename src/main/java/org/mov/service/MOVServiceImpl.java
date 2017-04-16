package org.mov.service;

import org.mov.entity.*;
import org.mov.repository.*;
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
    private DocumentTypeRepository documentTypeRepository;
    private TagRepository tagRepository;
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    public MOVServiceImpl(UserRepository userRepository,
                          DocumentRepository documentRepository,
                          ThemeRepository themeRepository,
                          CountryRepository countryRepository,
                          DocumentTypeRepository documentTypeRepository,
                          TagRepository tagRepository,
                          SubCategoryRepository subCategoryRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.themeRepository = themeRepository;
        this.countryRepository = countryRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.tagRepository = tagRepository;
        this.subCategoryRepository = subCategoryRepository;
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
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
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

    @Override
    public void saveDocumentType(DocumentType documentType) {
        documentTypeRepository.saveDocumentType(documentType);
    }

    @Override
    public void removeDocumentType(DocumentType documentType) {
        documentTypeRepository.removeDocumentType(documentType);
    }

    @Override
    public DocumentType findDocumentTypeById(Long id) {
        return documentTypeRepository.findDocumentTypeById(id);
    }

    @Override
    public DocumentType findDocumentTypeByName(String name) {
        return documentTypeRepository.findDocumentTypeByName(name);
    }

    @Override
    public Collection<DocumentType> findAllDocumentTypes() {
        return documentTypeRepository.findAllDocumentTypes();
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.saveTag(tag);
    }

    @Override
    public void removeTag(Tag tag) {
        tagRepository.removeTag(tag);
    }

    @Override
    public Tag findTagById(Long id) {
        return tagRepository.findTagById(id);
    }

    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public Collection<Tag> findAllTags() {
        return tagRepository.findAllTags();
    }

    @Override
    public void saveSubCategory(SubCategory subCategory) {
        subCategoryRepository.saveSubCategory(subCategory);
    }

    @Override
    public void removeSubCategory(SubCategory subCategory) {
        subCategoryRepository.removeSubCategory(subCategory);
    }

    @Override
    public SubCategory findSubCategoryById(Long id) {
        return subCategoryRepository.findSubCategoryById(id);
    }

    @Override
    public Collection<SubCategory> findAllSubCategories() {
        return subCategoryRepository.findAllSubCategories();
    }
}
