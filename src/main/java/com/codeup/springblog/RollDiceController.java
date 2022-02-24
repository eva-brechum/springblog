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
        Random rn = new Random();
        int diceRoll = rn.nextInt(6 -1 + 1);
//        1 + (int) (Math.random() * ((7 - 1) + 1))
        model.addAttribute("guess", n);
        model.addAttribute("rolled", diceRoll);
//        return "roll-dice";
    }
}
