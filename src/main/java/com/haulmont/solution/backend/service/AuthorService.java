package com.haulmont.solution.backend.service;

import com.haulmont.solution.backend.entity.AuthorEntity;

import java.util.Collection;

public interface AuthorService {
    Collection<AuthorEntity> getAllAuthors();

    void saveAuthor(AuthorEntity authorEntity);
    void removeAuthor(AuthorEntity authorEntity);
}
