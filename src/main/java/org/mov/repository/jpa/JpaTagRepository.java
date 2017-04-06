package org.mov.repository.jpa;

import org.mov.model.Tag;
import org.mov.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
public class JpaTagRepository implements TagRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveTag(Tag tag) {
        if (tag.getId() == null) {
            em.persist(tag);
        } else {
            em.merge(tag);
        }
    }

    @Override
    public void removeTag(Tag tag) {
        em.remove(em.merge(tag));
    }

    @Override
    public Tag findTagById(Long id) {
        return em.find(Tag.class, id);
    }

    @Override
    public Tag findTagByName(String name) {
        Query query = em.createQuery("SELECT tag FROM Tag tag WHERE tag.name =:name");
        query.setParameter("name", name);
        List tags = query.getResultList();
        if (tags.size() > 0) return (Tag) tags.get(0);
        else return null;
    }

    @Override
    public Collection<Tag> findAllTags() {
        return em.createQuery("SELECT tag FROM Tag tag", Tag.class).getResultList();
    }
}
