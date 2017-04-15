package org.mov.api;

import org.mov.entity.DocumentType;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class DocumentTypeController {
    private MOVService movService;

    @Autowired
    public DocumentTypeController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DocumentType> showCategories() {
        return new ArrayList<>(movService.findAllDocumentTypes());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void addDocumentType(@RequestBody DocumentType documentType) {
        movService.saveDocumentType(documentType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void saveDocumentType(@PathVariable("id") Long id,
                                 @RequestBody DocumentType documentType) {
        documentType.setId(id);
        movService.saveDocumentType(documentType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeDocumentType(@PathVariable("id") Long id) {
        DocumentType documentType = new DocumentType();
        documentType.setId(id);
        movService.removeDocumentType(documentType);
    }
}
