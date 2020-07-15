package com.malik.ithar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Blogpost.
 */
@Entity
@Table(name = "blogpost")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blogpost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "intro", nullable = false)
    private String intro;

    
    @Lob
    @Column(name = "blog", nullable = false)
    private String blog;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private Author author;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Blogpost title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public Blogpost intro(String intro) {
        this.intro = intro;
        return this;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBlog() {
        return blog;
    }

    public Blogpost blog(String blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Blogpost created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Author getAuthor() {
        return author;
    }

    public Blogpost author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blogpost)) {
            return false;
        }
        return id != null && id.equals(((Blogpost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Blogpost{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", intro='" + getIntro() + "'" +
            ", blog='" + getBlog() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
