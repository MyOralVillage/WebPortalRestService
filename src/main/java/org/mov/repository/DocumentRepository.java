package org.mov.repository;

import org.mov.model.Document;
import org.springframework.data.repository.Repository;

public interface DocumentRepository extends Repository<Document, Long> {
    void saveDocument(Document document);

    void removeDocument(Document document);

    Document findDocumentById(Long id);
}
