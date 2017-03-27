package org.mov.api;

import org.mov.model.Document;
import org.mov.model.User;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    private MOVService movService;

    @Autowired
    public DocumentController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Document> showDocumentList(
            @RequestParam(value = "theme", required = false) final String theme,
            @RequestParam(value = "country", required = false) final String country,
            @RequestParam(value = "documentType", required = false) final String documentType) {

        // TODO: temporal solution. Should be replaced with query builder or proper JPQL
        Stream<Document> documentStream = new ArrayList<>(movService.findAllDocuments()).stream();
        if (theme != null)
            documentStream = documentStream.filter(document -> document.getTheme().getName().equals(theme));
        if (country != null)
            documentStream = documentStream.filter(document -> document.getCountry().getName().equals(country));
        if (documentType != null)
            documentStream = documentStream.filter(document -> document.getType().name().equals(documentType));

        return documentStream.collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document showDocumentById(@PathVariable("id") long id) {
        return movService.findDocumentById(id);
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody Document document) {
        User userCreated = movService.findUserByEmail(document.getUserCreated().getEmail());
        document.setUserCreated(userCreated);
        document.setUserUpdated(userCreated);
        document.setDateCreated(Date.from(Instant.now()));
        document.setDateUpdated(Date.from(Instant.now()));
        document.setCountry(movService.findCountryByName(document.getCountry().getName()));
        document.setTheme(movService.findThemeByName(document.getTheme().getName()));
        movService.saveDocument(document);
    }
}