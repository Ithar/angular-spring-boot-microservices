package com.malik.ithar.web.rest;

import com.malik.ithar.domain.Blogpost;
import com.malik.ithar.repository.BlogpostRepository;
import com.malik.ithar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.malik.ithar.domain.Blogpost}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BlogpostResource {

    private final Logger log = LoggerFactory.getLogger(BlogpostResource.class);

    private static final String ENTITY_NAME = "blogBlogpost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogpostRepository blogpostRepository;

    public BlogpostResource(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    /**
     * {@code POST  /blogposts} : Create a new blogpost.
     *
     * @param blogpost the blogpost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogpost, or with status {@code 400 (Bad Request)} if the blogpost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogposts")
    public ResponseEntity<Blogpost> createBlogpost(@Valid @RequestBody Blogpost blogpost) throws URISyntaxException {
        log.debug("REST request to save Blogpost : {}", blogpost);
        if (blogpost.getId() != null) {
            throw new BadRequestAlertException("A new blogpost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Blogpost result = blogpostRepository.save(blogpost);
        return ResponseEntity.created(new URI("/api/blogposts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blogposts} : Updates an existing blogpost.
     *
     * @param blogpost the blogpost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogpost,
     * or with status {@code 400 (Bad Request)} if the blogpost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogpost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blogposts")
    public ResponseEntity<Blogpost> updateBlogpost(@Valid @RequestBody Blogpost blogpost) throws URISyntaxException {
        log.debug("REST request to update Blogpost : {}", blogpost);
        if (blogpost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Blogpost result = blogpostRepository.save(blogpost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blogpost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blogposts} : get all the blogposts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogposts in body.
     */
    @GetMapping("/blogposts")
    public List<Blogpost> getAllBlogposts() {
        log.debug("REST request to get all Blogposts");
        return blogpostRepository.findAll();
    }

    /**
     * {@code GET  /blogposts/:id} : get the "id" blogpost.
     *
     * @param id the id of the blogpost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogpost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blogposts/{id}")
    public ResponseEntity<Blogpost> getBlogpost(@PathVariable Long id) {
        log.debug("REST request to get Blogpost : {}", id);
        Optional<Blogpost> blogpost = blogpostRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blogpost);
    }

    /**
     * {@code DELETE  /blogposts/:id} : delete the "id" blogpost.
     *
     * @param id the id of the blogpost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogposts/{id}")
    public ResponseEntity<Void> deleteBlogpost(@PathVariable Long id) {
        log.debug("REST request to delete Blogpost : {}", id);
        blogpostRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
