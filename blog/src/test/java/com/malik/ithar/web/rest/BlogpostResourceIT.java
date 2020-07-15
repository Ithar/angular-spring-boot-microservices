package com.malik.ithar.web.rest;

import com.malik.ithar.BlogApp;
import com.malik.ithar.domain.Blogpost;
import com.malik.ithar.repository.BlogpostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.malik.ithar.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BlogpostResource} REST controller.
 */
@SpringBootTest(classes = BlogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BlogpostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRO = "AAAAAAAAAA";
    private static final String UPDATED_INTRO = "BBBBBBBBBB";

    private static final String DEFAULT_BLOG = "AAAAAAAAAA";
    private static final String UPDATED_BLOG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BlogpostRepository blogpostRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogpostMockMvc;

    private Blogpost blogpost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blogpost createEntity(EntityManager em) {
        Blogpost blogpost = new Blogpost()
            .title(DEFAULT_TITLE)
            .intro(DEFAULT_INTRO)
            .blog(DEFAULT_BLOG)
            .created(DEFAULT_CREATED);
        return blogpost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blogpost createUpdatedEntity(EntityManager em) {
        Blogpost blogpost = new Blogpost()
            .title(UPDATED_TITLE)
            .intro(UPDATED_INTRO)
            .blog(UPDATED_BLOG)
            .created(UPDATED_CREATED);
        return blogpost;
    }

    @BeforeEach
    public void initTest() {
        blogpost = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogpost() throws Exception {
        int databaseSizeBeforeCreate = blogpostRepository.findAll().size();
        // Create the Blogpost
        restBlogpostMockMvc.perform(post("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogpost)))
            .andExpect(status().isCreated());

        // Validate the Blogpost in the database
        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeCreate + 1);
        Blogpost testBlogpost = blogpostList.get(blogpostList.size() - 1);
        assertThat(testBlogpost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlogpost.getIntro()).isEqualTo(DEFAULT_INTRO);
        assertThat(testBlogpost.getBlog()).isEqualTo(DEFAULT_BLOG);
        assertThat(testBlogpost.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createBlogpostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogpostRepository.findAll().size();

        // Create the Blogpost with an existing ID
        blogpost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogpostMockMvc.perform(post("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogpost)))
            .andExpect(status().isBadRequest());

        // Validate the Blogpost in the database
        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogpostRepository.findAll().size();
        // set the field null
        blogpost.setTitle(null);

        // Create the Blogpost, which fails.


        restBlogpostMockMvc.perform(post("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogpost)))
            .andExpect(status().isBadRequest());

        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntroIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogpostRepository.findAll().size();
        // set the field null
        blogpost.setIntro(null);

        // Create the Blogpost, which fails.


        restBlogpostMockMvc.perform(post("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogpost)))
            .andExpect(status().isBadRequest());

        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlogposts() throws Exception {
        // Initialize the database
        blogpostRepository.saveAndFlush(blogpost);

        // Get all the blogpostList
        restBlogpostMockMvc.perform(get("/api/blogposts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogpost.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].intro").value(hasItem(DEFAULT_INTRO)))
            .andExpect(jsonPath("$.[*].blog").value(hasItem(DEFAULT_BLOG.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }
    
    @Test
    @Transactional
    public void getBlogpost() throws Exception {
        // Initialize the database
        blogpostRepository.saveAndFlush(blogpost);

        // Get the blogpost
        restBlogpostMockMvc.perform(get("/api/blogposts/{id}", blogpost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogpost.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.intro").value(DEFAULT_INTRO))
            .andExpect(jsonPath("$.blog").value(DEFAULT_BLOG.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }
    @Test
    @Transactional
    public void getNonExistingBlogpost() throws Exception {
        // Get the blogpost
        restBlogpostMockMvc.perform(get("/api/blogposts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogpost() throws Exception {
        // Initialize the database
        blogpostRepository.saveAndFlush(blogpost);

        int databaseSizeBeforeUpdate = blogpostRepository.findAll().size();

        // Update the blogpost
        Blogpost updatedBlogpost = blogpostRepository.findById(blogpost.getId()).get();
        // Disconnect from session so that the updates on updatedBlogpost are not directly saved in db
        em.detach(updatedBlogpost);
        updatedBlogpost
            .title(UPDATED_TITLE)
            .intro(UPDATED_INTRO)
            .blog(UPDATED_BLOG)
            .created(UPDATED_CREATED);

        restBlogpostMockMvc.perform(put("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlogpost)))
            .andExpect(status().isOk());

        // Validate the Blogpost in the database
        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeUpdate);
        Blogpost testBlogpost = blogpostList.get(blogpostList.size() - 1);
        assertThat(testBlogpost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogpost.getIntro()).isEqualTo(UPDATED_INTRO);
        assertThat(testBlogpost.getBlog()).isEqualTo(UPDATED_BLOG);
        assertThat(testBlogpost.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogpost() throws Exception {
        int databaseSizeBeforeUpdate = blogpostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogpostMockMvc.perform(put("/api/blogposts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blogpost)))
            .andExpect(status().isBadRequest());

        // Validate the Blogpost in the database
        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogpost() throws Exception {
        // Initialize the database
        blogpostRepository.saveAndFlush(blogpost);

        int databaseSizeBeforeDelete = blogpostRepository.findAll().size();

        // Delete the blogpost
        restBlogpostMockMvc.perform(delete("/api/blogposts/{id}", blogpost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Blogpost> blogpostList = blogpostRepository.findAll();
        assertThat(blogpostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
