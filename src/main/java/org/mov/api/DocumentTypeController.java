package org.mov.api;

import org.mov.model.DocumentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/document-type")
public class DocumentTypeController {
    @RequestMapping(method = RequestMethod.GET)
    public List<String> showCategories() {
        List<String> categories = new ArrayList<>();
        for (DocumentType type : DocumentType.values()) {
            categories.add(type.name());
        }
        return categories;
    }
}
