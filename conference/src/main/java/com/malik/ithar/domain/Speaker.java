package com.malik.ithar.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Speaker.
 */
@Entity
@Table(name = "speaker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Speaker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    
    @Lob
    @Column(name = "boi", nullable = false)
    private byte[] boi;

    @Column(name = "boi_content_type", nullable = false)
    private String boiContentType;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "twitter")
    private String twitter;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "speaker_sessions",
               joinColumns = @JoinColumn(name = "speaker_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sessions_id", referencedColumnName = "id"))
    private Set<Session> sessions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Speaker firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Speaker lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getBoi() {
        return boi;
    }

    public Speaker boi(byte[] boi) {
        this.boi = boi;
        return this;
    }

    public void setBoi(byte[] boi) {
        this.boi = boi;
    }

    public String getBoiContentType() {
        return boiContentType;
    }

    public Speaker boiContentType(String boiContentType) {
        this.boiContentType = boiContentType;
        return this;
    }

    public void setBoiContentType(String boiContentType) {
        this.boiContentType = boiContentType;
    }

    public String getEmail() {
        return email;
    }

    public Speaker email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public Speaker twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Speaker sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Speaker addSessions(Session session) {
        this.sessions.add(session);
        session.getSpeakers().add(this);
        return this;
    }

    public Speaker removeSessions(Session session) {
        this.sessions.remove(session);
        session.getSpeakers().remove(this);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Speaker)) {
            return false;
        }
        return id != null && id.equals(((Speaker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Speaker{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", boi='" + getBoi() + "'" +
            ", boiContentType='" + getBoiContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", twitter='" + getTwitter() + "'" +
            "}";
    }
}
