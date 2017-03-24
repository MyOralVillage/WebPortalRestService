package org.mov.repository;

import org.mov.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);
}
