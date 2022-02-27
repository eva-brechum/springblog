package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
    //posts index page
    @GetMapping("/posts")
    @ResponseBody
    public String post () {
        return "posts index page";
    }

    //view an individual post
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String getPost(@PathVariable long id) {
        return "view an individual post";
    }
    //view the form for creating a post
    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreateForm() {
        return "view the form for creating a post";
    }

    //create a new post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "create a new page";
    }
}
