package org.mov.repository.jpa;

import org.mov.entity.Theme;
import org.mov.repository.ThemeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class JpaThemeRepository implements ThemeRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveTheme(Theme theme) {
        if (theme.getId() == null) {
            em.persist(theme);
        } else {
            em.merge(theme);
        }
    }

    @Override
    public void removeTheme(Theme theme) {
        em.remove(em.merge(theme));
    }

    @Override
    public Theme findThemeById(Long id) {
        return em.find(Theme.class, id);
    }

    @Override
    public Theme findThemeByName(String name) {
        Query query = em.createQuery("SELECT theme FROM Theme theme WHERE theme.name =:name");
        query.setParameter("name", name);
        return (Theme) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Theme> findAllThemes() {
        return em.createQuery("SELECT t FROM Theme t").getResultList();
    }
}
