package com.haulmont.solution.backend.service;

import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.backend.projection.BookViewProjection;

import java.util.Collection;

public interface BookService {
    Collection<BookEntity> getAllBooks();
    Collection<BookViewProjection> getProjectionViewBooks();

    BookEntity getBookById(Long id);

    void saveBook(BookEntity bookEntity);
    void removeBook(BookEntity bookEntity);
}
