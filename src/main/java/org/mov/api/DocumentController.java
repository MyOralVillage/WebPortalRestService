package org.mov.api;

import org.mov.model.Document;
import org.mov.model.User;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    private MOVService movService;

    @Autowired
    public DocumentController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document showDocumentById(@PathVariable("id") long id) {
        return movService.findDocumentById(id);
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
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