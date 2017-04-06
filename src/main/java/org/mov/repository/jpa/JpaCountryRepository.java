package org.mov.repository.jpa;

import org.mov.entity.Country;
import org.mov.repository.CountryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class JpaCountryRepository implements CountryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveCountry(Country country) {
        if (country.getId() == null) {
            em.persist(country);
        } else {
            em.merge(country);
        }
    }

    @Override
    public void removeCountry(Country country) {
        em.remove(em.merge(country));
    }

    @Override
    public Country findCountryById(Long id) {
        return em.find(Country.class, id);
    }

    @Override
    public Country findCountryByName(String name) {
        Query query = em.createQuery("SELECT country FROM Country country WHERE country.name =:name");
        query.setParameter("name", name);
        return (Country) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Country> findAllCountries() {
        return em.createQuery("SELECT c FROM Country c").getResultList();
    }
}
