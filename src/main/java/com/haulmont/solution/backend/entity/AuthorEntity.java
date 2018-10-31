package com.haulmont.solution.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "AUTHOR", schema = "PUBLIC", catalog = "PUBLIC")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @NotNull(message = "Please fill Name")
    @Size(max = 100, message = "Name has a lot of symbols")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotNull(message = "Please fill Last Name")
    @Size(max = 100, message = "Last Name has a lot of symbols")
    @Column(name = "LASTNAME", nullable = false, length = 100)
    private String lastName;

    @NotNull(message = "Please fill Middle Name")
    @Size(max = 100, message = "Middle Name has a lot of symbols")
    @Column(name = "MIDDLENAME", nullable = false, length = 100)
    private String middleName;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, middleName);
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
