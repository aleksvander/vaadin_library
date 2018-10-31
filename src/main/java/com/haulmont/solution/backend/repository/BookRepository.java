package com.haulmont.solution.backend.repository;

import com.haulmont.solution.backend.entity.BookEntity;
import com.haulmont.solution.backend.projection.BookViewProjection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookRepository  extends JpaRepository<BookEntity, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Collection<BookViewProjection> getAllBooksBy();

    @EntityGraph(attributePaths = {"author", "genre"})
    BookEntity getBooksById(Long id);
}
