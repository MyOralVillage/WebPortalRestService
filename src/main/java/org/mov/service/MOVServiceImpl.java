package org.mov.service;

import org.mov.model.Document;
import org.mov.model.User;
import org.mov.repository.DocumentRepository;
import org.mov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MOVServiceImpl implements MOVService {
    private DocumentRepository documentRepository;
    private UserRepository userRepository;

    @Autowired
    public MOVServiceImpl(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public void saveDocument(Document document) {
        documentRepository.saveDocument(document);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        userRepository.removeUser(user);
    }

    @Override
    @Transactional
    public void removeDocument(Document document) {
        documentRepository.removeDocument(document);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Document findDocumentById(Long id) {
        return documentRepository.findDocumentById(id);
    }
}
