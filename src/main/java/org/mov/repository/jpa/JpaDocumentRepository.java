package org.mov.repository.jpa;

import org.mov.entity.Document;
import org.mov.repository.DocumentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class JpaDocumentRepository implements DocumentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveDocument(Document document) {
        if (document.getId() == null) {
            em.persist(document);
        } else {
            em.merge(document);
        }
    }

    @Override
    public void removeDocument(Document document) {
        em.remove(em.merge(document));
    }

    @Override
    public Document findDocumentById(Long id) {
        return em.find(Document.class, id);
    }

    @Override
    public Collection<Document> findAllDocuments() {
        return em.createQuery("SELECT document FROM Document document").getResultList();
    }
}
