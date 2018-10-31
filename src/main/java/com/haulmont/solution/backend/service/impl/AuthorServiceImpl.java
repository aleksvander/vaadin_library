package com.haulmont.solution.backend.service.impl;

import com.haulmont.solution.backend.entity.AuthorEntity;
import com.haulmont.solution.backend.repository.AuthorRepository;
import com.haulmont.solution.backend.service.AuthorService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthorServiceImpl implements AuthorService
{
    AuthorRepository authorRepository;

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Collection<AuthorEntity> getAllAuthors()
    {
        return authorRepository.findAll();
    }

    @Override
    public void saveAuthor(AuthorEntity authorEntity)
    {
        authorRepository.save(authorEntity);
    }

    @Override
    public void removeAuthor(AuthorEntity authorEntity) throws ConstraintViolationException
    {
        try
        {
            authorRepository.delete(authorEntity);
        }
        catch (DataIntegrityViolationException e)
        {
            Throwable cause = e.getCause();
            throw (cause instanceof ConstraintViolationException) ? ((ConstraintViolationException) cause) : e;
        }
    }
}
