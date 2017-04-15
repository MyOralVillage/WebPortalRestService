package org.mov.api;

import org.mov.entity.*;
import org.mov.model.file.FileIO;
import org.mov.service.MOVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/contentItems")
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    private final MOVService movService;
    private final FileIO fileIoHandler;

    @Autowired
    public DocumentController(MOVService movService, FileIO fileIoHandler) {
        this.movService = movService;
        this.fileIoHandler = fileIoHandler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Document> showDocumentList(
            @RequestParam(value = "subCategoryName", required = false) final String subCategoryName,
            @RequestParam(value = "subCategoryValue", required = false) final String subCategoryValue,
            @RequestParam(value = "postedBy", required = false) final String postedBy,
            @RequestParam(value = "category", required = false) final String category,
            @RequestParam(value = "theme", required = false) final String theme,
            @RequestParam(value = "country", required = false) final String country,
            @RequestParam(value = "sortBy", required = false) final String sortBy,
            @RequestParam(value = "sortAsc", required = false) final Boolean sortAsc,
            @RequestParam(value = "skip", required = false) final Integer skip,
            @RequestParam(value = "limit", required = false) final Integer limit) {

        Stream<Document> docStream = new ArrayList<>(movService.findAllDocuments()).stream();
        if (subCategoryName != null && subCategoryValue != null) {
            SubCategory toSearch = new SubCategory();
            toSearch.setName(subCategoryName);
            toSearch.setValue(subCategoryValue);
            docStream = docStream.filter(document -> document.getSubCategories().contains(toSearch));
        }
        if (postedBy != null)
            docStream = docStream.filter(document -> document.getPostedBy().getUsername().equals(postedBy));
        if (category != null)
            docStream = docStream.filter(document -> document.getCategory().getName().equals(category.toUpperCase()));
        if (theme != null)
            docStream = docStream.filter(document -> document.getTheme().getName().equals(theme.toUpperCase()));
        if (country != null)
            docStream = docStream.filter(document -> document.getCountry().getName().equals(country));

        docStream = docStream.sorted(this.sortDocumentBy(sortBy, sortAsc == null ? true : sortAsc));

        if (skip != null)
            docStream = docStream.skip(skip);
        if (limit != null)
            docStream = docStream.limit(limit);

        return docStream.collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document showDocumentById(@PathVariable("id") Long id) {
        return movService.findDocumentById(id);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody Document document) {
        User userCreated = movService.findUserByEmail(document.getPostedBy().getEmail());
        DocumentType documentType = movService.findDocumentTypeByName(document.getCategory().getName());
        Country country = movService.findCountryByName(document.getCountry().getName());
        Theme theme = movService.findThemeByName(document.getTheme().getName());

        if (documentType == null) {
            documentType = document.getCategory();
            movService.saveDocumentType(documentType);
        }
        if (theme == null) {
            theme = document.getTheme();
            movService.saveTheme(theme);
        }

        document.setPostedBy(userCreated);
        document.setUserUpdated(userCreated);
        document.setCategory(documentType);
        document.setCountry(country);
        document.setTheme(theme);

        Set<Tag> tags = document.getTags();
        tags = tags.stream().map(tag -> {
            Tag newTag = movService.findTagByName(tag.getName());
            if (newTag == null) {
                movService.saveTag(tag);
                return tag;
            } else {
                return newTag;
            }
        }).collect(Collectors.toSet());
        document.setTags(tags);

        Set<SubCategory> subCategories = document.getSubCategories();
        subCategories = subCategories.stream().map(subCategory -> {
            SubCategory newSubCategory = movService.findSubCategoryById(subCategory.getId());
            if (newSubCategory == null) {
                movService.saveSubCategory(subCategory);
                return subCategory;
            } else return newSubCategory;
        }).collect(Collectors.toSet());
        document.setSubCategories(subCategories);

        movService.saveDocument(document);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeDocument(@PathVariable("id") Long id) {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        movService.removeSubCategory(subCategory);
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public void uploadDocumentFile(@PathVariable("id") Long id,
                                   @RequestParam("file") MultipartFile file) {
        Document document = movService.findDocumentById(id);
        String path = String.format("/%s/%s/%s/%s.%s", document.getCountry().getName(), document.getTheme().getName(),
                document.getCategory().getName(), document.getName(), document.getFileExtension());

        Path uploadFile = Paths.get("temp" + path);

        try {
            if (!Files.exists(uploadFile.getParent())) Files.createDirectories(uploadFile.getParent());
            Files.deleteIfExists(uploadFile);
            Files.createFile(uploadFile);
            file.transferTo(uploadFile.toAbsolutePath().toFile());
            fileIoHandler.uploadFile(uploadFile.toFile(), path);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                Files.deleteIfExists(uploadFile);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void getFile(@PathVariable("id") Long id, HttpServletResponse response) {
        Document document = movService.findDocumentById(id);
        String path = String.format("/%s/%s/%s/%s.%s", document.getCountry().getName(), document.getTheme().getName(),
                document.getCategory().getName(), document.getName(), document.getFileExtension());

        try {
            OutputStream outputStream = response.getOutputStream();
            fileIoHandler.downloadFile(outputStream, path);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Comparator<Document> sortDocumentBy(String sortBy, boolean asc) {
        sortBy = sortBy == null ? "DATE_MODIFIED" : sortBy;
        switch (sortBy.toUpperCase()) {
            case "NAME":
                if (asc) return Comparator.comparing(Document::getName);
                else return Comparator.comparing(Document::getName, Comparator.reverseOrder());
            case "DATE_CREATED":
                if (asc) return Comparator.comparing(MonitoredEntity::getDateCreated);
                else return Comparator.comparing(MonitoredEntity::getDateCreated, Comparator.reverseOrder());
            default:
                if (asc) return Comparator.comparing(MonitoredEntity::getDateModified);
                else return Comparator.comparing(MonitoredEntity::getDateModified, Comparator.reverseOrder());
        }
    }
}