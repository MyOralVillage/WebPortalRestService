package org.mov.repository;

import org.mov.entity.Document;

import java.util.Collection;

public interface DocumentRepository {
    void saveDocument(Document document);

    void removeDocument(Document document);

    Document findDocumentById(Long id);

    Collection<Document> findAllDocuments();
}
