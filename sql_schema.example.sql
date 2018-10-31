CREATE TABLE library_schema.author
(
	id         BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	lastname   VARCHAR(100) NOT NULL,
	middlename VARCHAR(100) NOT NULL,
	name       VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE library_schema.book
(
	id               BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	city             VARCHAR(100),
	name             VARCHAR(100) NOT NULL,
	publication_year DATE,
	publisher        VARCHAR(100),
	author_id        BIGINT NOT NULL,
	genre_id         BIGINT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE library_schema.genre
(
	id   BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	name VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

DROP INDEX author_id_uindex;

CREATE UNIQUE INDEX "AUTHOR_ID_UINDEX"
	ON library_schema.author (id);

DROP INDEX book_id_uindex;

CREATE UNIQUE INDEX "BOOK_ID_UINDEX"
	ON library_schema.book (id);

DROP INDEX genre_id_uindex;

CREATE UNIQUE INDEX "GENRE_ID_UINDEX"
	ON library_schema.genre (id);

DROP INDEX genre_name_uindex;

CREATE UNIQUE INDEX "GENRE_NAME_UINDEX"
	ON library_schema.genre (name);

ALTER TABLE library_schema.book
	DROP CONSTRAINT book_author_id_fk;

ALTER TABLE library_schema.book
	ADD CONSTRAINT book_author_id_fk FOREIGN KEY (author_id) REFERENCES library_schema.author (id);

ALTER TABLE library_schema.book
	DROP CONSTRAINT book_genre_id_fk;

ALTER TABLE library_schema.book
	ADD CONSTRAINT book_genre_id_fk FOREIGN KEY (genre_id) REFERENCES library_schema.genre (id);