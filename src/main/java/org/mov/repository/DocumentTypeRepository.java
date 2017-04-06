package org.mov.repository;

import org.mov.entity.DocumentType;

import java.util.Collection;

public interface DocumentTypeRepository {
    void saveDocumentType(DocumentType documentType);

    void removeDocumentType(DocumentType documentType);

    DocumentType findDocumentTypeById(Long id);

    DocumentType findDocumentTypeByName(String name);

    Collection<DocumentType> findAllDocumentTypes();
}
