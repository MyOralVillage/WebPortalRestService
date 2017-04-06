package org.mov.repository;

import org.mov.entity.User;

public interface UserRepository {
    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);
}
