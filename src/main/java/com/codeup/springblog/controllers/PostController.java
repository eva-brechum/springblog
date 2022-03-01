package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;

    public void setUsersDao(PostRepository postsDao, UserRepository usersDao) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
    }

    //posts index page
    @GetMapping("/posts")
    public String posts(Model model){
        List<Post> allThePosts = new ArrayList<>();
        Post p2 = new Post(2, "Default", "to test if it works");
        allThePosts.add(p2);

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
        model.addAttribute("individualPost" , postsDao.getById(id));

        return "posts/show";
    }
//    @ResponseBody
//    public String getPost(@PathVariable long id) {
//        return "view an individual post";
//    }
    //view the form for creating a post
    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreateForm() {
        return "post/create";
    }


    //create a new post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
      Post newPost = new Post(title, body);
      newPost.setUser(usersDao.getById(1L));
      postsDao.save(newPost);
        return "redirect:/posts";
    }
    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable long id, Model model) {
        Post postEdit = postsDao.getById(id);
        model.addAttribute("postToEdit", postEdit);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@RequestParam(name = "title") String title, @RequestParam(name= "body") String body, @PathVariable long id) {
        Post postEdit = postsDao.getById(id);
        postEdit.setTitle(title);
        postEdit.setBody(body);
        postsDao.save(postEdit);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }

}
