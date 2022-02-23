package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {
    @RequestMapping(path= "/add/{number1}/and/{number2}", method= RequestMethod.GET)
    @ResponseBody
    ///add/3/and/4
    public int add(@PathVariable int number1, @PathVariable int number2) {
        return number1 + number2;
    }
    //walkthrough solution
//    public String addition(@PathVariable int num1, @PathVariable int num2){
//        return "The answer is: " + (num1 + num2);
//    }
   // /subtract/3/from/10
    @GetMapping("/subtract/{number1}/from/{number2}")
    @ResponseBody
    public int subtract(@PathVariable int number1, @PathVariable int number2) {
        return number2 -number1;
    }

  // /multiply/4/and/5
    @GetMapping("/multiply/{number1}/and/{number2}")
    @ResponseBody
    public int multiply(@PathVariable int number1, @PathVariable int number2) {
        return number1 * number2;

    }

   // /divide/6/by/3
    @GetMapping("divide/{number1}/by/{number2}")
    @ResponseBody
    public double divide(@PathVariable double number1, @PathVariable double number2) {
        return number1 / number2;
    }
}
