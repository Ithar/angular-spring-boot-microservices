package com.malik.ithar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.malik.ithar.web.rest.TestUtil;

public class BlogpostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blogpost.class);
        Blogpost blogpost1 = new Blogpost();
        blogpost1.setId(1L);
        Blogpost blogpost2 = new Blogpost();
        blogpost2.setId(blogpost1.getId());
        assertThat(blogpost1).isEqualTo(blogpost2);
        blogpost2.setId(2L);
        assertThat(blogpost1).isNotEqualTo(blogpost2);
        blogpost1.setId(null);
        assertThat(blogpost1).isNotEqualTo(blogpost2);
    }
}
