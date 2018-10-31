package com.haulmont.solution.backend.service.impl;

import com.haulmont.solution.backend.entity.GenreEntity;
import com.haulmont.solution.backend.repository.GenreRepository;
import com.haulmont.solution.backend.service.GenreService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreServiceImpl implements GenreService {

    GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Collection<GenreEntity> getAllGenres()
    {
        return genreRepository.findAll();
    }

    @Override
    public void saveGenre(GenreEntity genreEntity)
    {
        genreRepository.save(genreEntity);
    }

    @Override
    public void removeGenre(GenreEntity genreEntity)
    {
        try
        {
            genreRepository.delete(genreEntity);
        }
        catch (DataIntegrityViolationException e)
        {
            Throwable cause = e.getCause();
            throw (cause instanceof ConstraintViolationException) ? ((ConstraintViolationException) cause) : e;
        }
    }
}
