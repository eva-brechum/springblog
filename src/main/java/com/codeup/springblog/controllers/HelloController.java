package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    // Use curly braces to establish Path Variable in the naming definition
    @GetMapping("/hello/{name}") //name references path variable
    @ResponseBody
    // Use annotation to get the value of the Path Variable
    public String hello(@PathVariable String name) {
        return "Hello, " + name + " !";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    //REQUEST MAPPING
    @RequestMapping(path = "increment/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String increment(@PathVariable int number) {
        return number + " plus one is " + (number + 1) + " !";
    }
}
