package com.haulmont.solution.backend.service;

import com.haulmont.solution.backend.entity.GenreEntity;

import java.util.Collection;

public interface GenreService {
    Collection<GenreEntity> getAllGenres();

    void saveGenre(GenreEntity genreEntity);
    void removeGenre(GenreEntity genreEntity);
}
