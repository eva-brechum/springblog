package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    //posts index page
    @GetMapping("/posts")
    public String posts(Model model){
        List<Post> allThePosts = new ArrayList<>();
        Post p2 = new Post(2, "Default", "to test if it works");
        allThePosts.add(p2);

        model.addAttribute("allThePosts" , allThePosts);
        return "posts/index";
    }
//    @ResponseBody
//    public String post () {
//        return "posts index page";
//    }

    //view an individual post
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable long id, Model model) {
        Post p1 = new Post(1, "Spring is near", "lorem ipsum");
        model.addAttribute("individualPost" , p1);
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
        return "view the form for creating a post";
    }

    //create a new post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
//      Post newPost = new Post(title, ;
//      postsDao.save(newPost);
        return "create a new page";
    }
}
