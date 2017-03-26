package org.mov.repository;

import org.mov.model.Document;

public interface DocumentRepository {
    void saveDocument(Document document);

    void removeDocument(Document document);

    Document findDocumentById(Long id);
}
