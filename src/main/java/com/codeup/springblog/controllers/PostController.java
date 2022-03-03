package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;
    private final EmailService emailService;

    public PostController(PostRepository postsDao, UserRepository usersDao, EmailService emailService) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.emailService = emailService;
    }


    //posts index page
    @GetMapping("/posts")
    public String posts(Model model) {
//        List<Post> allThePosts = new ArrayList<>();
//        Post p2 = new Post(2, "Default", "to test if it works");
//        allThePosts.add(p2);
//        model.addAttribute("allThePosts" , allThePosts);
        model.addAttribute("allThePosts", postsDao.findAll());
        return "posts/index";
    }
//    @ResponseBody
//    public String post () {
//        return "posts index page";
//    }

    //view an individual post
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable long id, Model model) {
        model.addAttribute("individualPost", postsDao.getById(id));
        return "posts/show";
    }

    //    @ResponseBody
//    public String getPost(@PathVariable long id) {
//        return "view an individual post";
//    }
    //view the form for creating a post
    @GetMapping("/posts/create")
    public String viewCreateForm(Model model) {
        model.addAttribute("newPost", new Post());
        return "posts/create";
    }


    //create a new post
    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post newPost) {
//      Post newPost = new Post(title, body);
        newPost.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        newPost.setUser(usersDao.getById(1L));
        emailService.prepareAndSend(newPost, "created post", "it worked");
        postsDao.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable long id, Model model) {
        Post postEdit = postsDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (postEdit.getUser().getId() == loggedInUser.getId()) {
        model.addAttribute("postEdit", postEdit);
            return "posts/edit";
        }else {
            return "redirect:/posts";
        }
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post postEdit, @PathVariable long id) {
        if(postsDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            postEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            postsDao.save(postEdit);
        }
//        Post postEdit = postsDao.getById(id);
//        postEdit.setTitle(title);
//        postEdit.setBody(body);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }
}



