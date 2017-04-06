package org.mov.api;

import org.mov.model.*;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public Document showDocumentById(@PathVariable("id") long id) {
        return movService.findDocumentById(id);
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody Document document) {
        User userCreated = movService.findUserByEmail(document.getUserCreated().getEmail());
        DocumentType documentType = movService.findDocumentTypeByName(document.getType().getName());
        Country country = movService.findCountryByName(document.getCountry().getName());
        Theme theme = movService.findThemeByName(document.getTheme().getName());

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
}