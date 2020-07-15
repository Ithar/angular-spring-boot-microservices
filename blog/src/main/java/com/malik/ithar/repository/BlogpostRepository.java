package com.malik.ithar.repository;

import com.malik.ithar.domain.Blogpost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Blogpost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {
}
