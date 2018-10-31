package com.haulmont.solution.backend.service.impl;

import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.backend.projection.BookViewProjection;
import com.haulmont.solution.backend.repository.BookRepository;
import com.haulmont.solution.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<BookEntity> getAllBooks()
    {
        return bookRepository.findAll();
    }

    @Override
    public Collection<BookViewProjection> getProjectionViewBooks()
    {
        return bookRepository.getAllBooksBy();
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRepository.getBooksById(id);
    }

    @Override
    public void saveBook(BookEntity bookEntity)
    {
        bookRepository.save(bookEntity);
    }

    @Override
    public void removeBook(BookEntity bookEntity)
    {
        bookRepository.delete(bookEntity);
    }
}
