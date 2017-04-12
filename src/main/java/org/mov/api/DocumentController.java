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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/document")
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
            @RequestParam(value = "theme", required = false) final String theme,
            @RequestParam(value = "country", required = false) final String country,
            @RequestParam(value = "documentType", required = false) final String documentType) {

        // TODO: temporal solution. Should be replaced with query builder or proper JPQL
        Stream<Document> documentStream = new ArrayList<>(movService.findAllDocuments()).stream();
        if (theme != null)
            documentStream = documentStream.filter(document ->
                    document.getTheme().getName().equals(theme.toUpperCase()));
        if (country != null)
            documentStream = documentStream.filter(document ->
                    document.getCountry().getName().equals(country));
        if (documentType != null)
            documentStream = documentStream.filter(document ->
                    document.getType().getName().equals(documentType.toUpperCase()));

        return documentStream.collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document showDocumentById(@PathVariable("id") Long id) {
        return movService.findDocumentById(id);
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody Document document) {
        User userCreated = movService.findUserByEmail(document.getUserCreated().getEmail());
        DocumentType documentType = movService.findDocumentTypeByName(document.getType().getName());
        Country country = movService.findCountryByName(document.getCountry().getName());
        Theme theme = movService.findThemeByName(document.getTheme().getName());

        if (documentType == null) {
            documentType = document.getType();
            movService.saveDocumentType(documentType);
        }
        if (theme == null) {
            theme = document.getTheme();
            movService.saveTheme(theme);
        }

        document.setUserCreated(userCreated);
        document.setUserUpdated(userCreated);
        document.setType(documentType);
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

        movService.saveDocument(document);
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public void uploadDocumentFile(@PathVariable("id") Long id,
                                   @RequestParam("file") MultipartFile file) {
        Document document = movService.findDocumentById(id);
        String path = String.format("/%s/%s/%s/%s.%s", document.getCountry().getName(), document.getTheme().getName(),
                document.getType().getName(), document.getTitle(), document.getFileExtension());

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
                document.getType().getName(), document.getTitle(), document.getFileExtension());

        try {
            OutputStream outputStream = response.getOutputStream();
            fileIoHandler.downloadFile(outputStream, path);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}