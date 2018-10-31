package com.haulmont.solution.backend.projection;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

public interface BookViewProjection
{
    long getId();

    String getName();

    String getPublisher();

    Date getPublicationYear();

    String getCity();

    @Value("#{target.author.lastName + ' ' + target.author.name + ' ' + target.author.middleName}")
    AuthorEntity getAuthor();

    @Value("#{target.genre.name}")
    GenreEntity getGenre();

    interface GenreEntity
    {
        String getName();
    }

    interface AuthorEntity
    {
        String getName();
        String getLastName();
        String getMiddleName();
    }
}
