package com.haulmont.solution.backend.entity;

import com.haulmont.solution.backend.validation.constraint.PublisherConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "BOOK", schema = "PUBLIC", catalog = "PUBLIC")
@NamedEntityGraph(name = "BookEntity.detail", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @NotNull(message = "Please fill Name")
    @Size(max = 100, message = "Name has a lot of symbols")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotNull(message = "Please select Author")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuthorEntity.class)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private AuthorEntity author;

    @NotNull(message = "Please select Genre")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = GenreEntity.class)
    @JoinColumn(name = "GENRE_ID", nullable = false)
    private GenreEntity genre;

    @PublisherConstraint
    @Size(max = 100, message = "Publisher has a lot of symbols")
    @Column(name = "PUBLISHER", nullable = true, length = 100)
    private String publisher;

    @Column(name = "PUBLICATION_YEAR", nullable = true)
    private Date publicationYear;

    @Size(max = 100, message = "City has a lot of symbols")
    @Column(name = "CITY", nullable = true, length = 100)
    private String city;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Date publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookEntity that = (BookEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(publicationYear, that.publicationYear) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, publisher, publicationYear, city);
    }
}
