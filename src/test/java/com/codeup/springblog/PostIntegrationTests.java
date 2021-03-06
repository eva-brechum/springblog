package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class PostIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if (testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setEmail("testUser@email.com");
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expect a redirection to the posts index page after being logged in
        httpSession = this.mvc.perform(
                        post("/login").with(csrf())
                                .param("username", "testUser")
                                .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    @Test
    public void testIfUserSessionIsActive() {
        // It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testPostsIndex() throws Exception {
        List<Post> posts = postsDao.findAll();
        this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("All the posts")));
        for (Post post : posts) {
            this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(post.getTitle())));
        }
    }

    @Test
    public void testShowPost() throws Exception {

        Post post = postsDao.findAll().get(0);

        // Makes a Get request to /posts/{id} and expect a redirection to the Ad show page
        this.mvc.perform(MockMvcRequestBuilders.get("/posts/" + post.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(post.getTitle())));
    }


    @Test
    public void testCreatePost() throws Exception {
        // Makes a Post request to /posts/create and expect a redirection to the Post
        this.mvc.perform(MockMvcRequestBuilders.post("/posts/create")
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("title", "test")
                        .param("body", "for sale"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testEditPost() throws Exception {
        // Gets the first Ad for tests purposes
        Post post = postsDao.findAll().get(0);

        // Makes a Post request to /ads/{id}/edit and expect a redirection to the Ad show page
        this.mvc.perform(
                        post("/posts/" + post.getId() + "/edit").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "edited title")
                                .param("description", "edited description"))
                .andExpect(status().is3xxRedirection());

        // Makes a GET request to /posts/{id} and expect a redirection to the Ad show page
        this.mvc.perform(MockMvcRequestBuilders.get("/posts/" + post.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("edited title")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("edited description")));
    }
}