package org.mov.repository;

import org.mov.model.User;

public interface UserRepository {
    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);
}
