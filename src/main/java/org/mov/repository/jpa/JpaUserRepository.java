package org.mov.repository.jpa;

import org.mov.entity.User;
import org.mov.entity.UserRole;
import org.mov.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        // TODO: change the way of persisting roles
        if (user.getId() == null) {
            em.persist(user);
            for (UserRole role : user.getRoles()) {
                role.setUserId(user.getId());
                em.persist(role);
            }
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
        List<User> list = query.getResultList();
        if (list.size() != 1) return null;
        else return list.get(0);
    }

    @Override
    public User findUserByUsername(String username) {
        Query query = em.createQuery("SELECT user FROM User user WHERE user.username =:username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.size() != 1) return null;
        else return users.get(0);
    }
}
