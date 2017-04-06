package org.mov.repository.jpa;

import org.mov.entity.DocumentType;
import org.mov.repository.DocumentTypeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class JpaDocumentTypeRepository implements DocumentTypeRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveDocumentType(DocumentType documentType) {
        if (documentType.getId() == null) {
            em.persist(documentType);
        } else {
            em.merge(documentType);
        }
    }

    @Override
    public void removeDocumentType(DocumentType documentType) {
        em.remove(em.merge(documentType));
    }

    @Override
    public DocumentType findDocumentTypeById(Long id) {
        return em.find(DocumentType.class, id);
    }

    @Override
    public DocumentType findDocumentTypeByName(String name) {
        Query query = em.createQuery("SELECT docType FROM DocumentType docType WHERE docType.name =:name");
        query.setParameter("name", name);
        return (DocumentType) query.getSingleResult();
    }

    @Override
    public Collection<DocumentType> findAllDocumentTypes() {
        return em.createQuery("SELECT docType FROM DocumentType docType", DocumentType.class).getResultList();
    }
}
