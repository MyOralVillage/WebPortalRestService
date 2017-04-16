package org.mov.repository.jpa;

import org.mov.entity.SubCategory;
import org.mov.repository.SubCategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class JpaSubCategoryRepository implements SubCategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveSubCategory(SubCategory subCategory) {
        if (subCategory.getId() == null) {
            em.persist(subCategory);
        } else {
            em.merge(subCategory);
        }
    }

    @Override
    public void removeSubCategory(SubCategory subCategory) {
        em.remove(em.merge(subCategory));
    }

    @Override
    public SubCategory findSubCategoryById(Long id) {
        return em.find(SubCategory.class, id);
    }

    @Override
    public Collection<SubCategory> findAllSubCategories() {
        return em.createQuery("SELECT sc FROM SubCategory sc", SubCategory.class).getResultList();
    }
}
