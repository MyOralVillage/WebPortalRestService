package org.mov.service;

import org.mov.model.Document;
import org.mov.model.User;

public interface MOVService {
    void saveUser(User user);

    void saveDocument(Document document);

    void removeUser(User user);

    void removeDocument(Document document);

    User findUserById(Long id);

    Document findDocumentById(Long id);
}
