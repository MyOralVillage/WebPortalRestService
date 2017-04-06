package org.mov.api;

import org.mov.model.DocumentType;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/document-type")
public class DocumentTypeController {
    private MOVService movService;

    @Autowired
    public DocumentTypeController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<String> showCategories() {
        return movService.findAllDocumentTypes()
                .stream()
                .map(DocumentType::getName)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody DocumentType documentType) {
        movService.saveDocumentType(documentType);
    }
}
