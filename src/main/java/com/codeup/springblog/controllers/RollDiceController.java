package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {
    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    public String rollDiceResults(@PathVariable int n, Model model) {

        int random = (int) (Math.random() * (6 - 1) + 1);
        model.addAttribute("raandm", random);

        if (n == random) {
            model.addAttribute("you guessed wrong", "guess");
        }else{ model.addAttribute("You rolled", "correctly");
//
        }
        return "roll-dice";
    }
}