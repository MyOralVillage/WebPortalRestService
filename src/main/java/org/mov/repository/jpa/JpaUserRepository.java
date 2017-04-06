package org.mov.repository.jpa;

import org.mov.entity.User;
import org.mov.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public void removeUser(User user) {
        em.remove(em.merge(user));
    }

    @Override
    public User findUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        Query query = em.createQuery("SELECT user FROM User user WHERE user.email =:email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
