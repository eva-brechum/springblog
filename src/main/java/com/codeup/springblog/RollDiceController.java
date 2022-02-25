package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {
    @GetMapping("/roll-dice")
    public String rollDice() {
        return "rollDice";
    }
    @GetMapping("/roll-dice/{n}")
    public void rollDice(@PathVariable int n, Model model) {
//
        int random = (int)(Math.random() * (6 -1) + 1);
        model.addAttribute("you guessed", "guess" + n);
        model.addAttribute("Dice you rolled", "rolled" + random);
//        return "rollDice";
    }
}
