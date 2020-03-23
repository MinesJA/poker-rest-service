package com.minesja.poker.pokerrestservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
public class CardController {

    private static final String template = "Card, %s!";

    @GetMapping("/cards/random")
    @ResponseBody
    public String getRandomCard() {
        return "Random Card";
    }

    @GetMapping("/cards")
    @ResponseBody
    public String getCards(@RequestParam Optional<String> suit, @RequestParam Optional<String> rank) {

        if(suit.isPresent() && rank.isPresent()){
            return suit.flatMap(s -> rank.map(r -> String.format("Return %s of %s", r, s)))
                    .orElse("Suit or rank or both not present");
        } else if(suit.isPresent()){
            return suit.map(s -> String.format("Return all the cards of suit %s", s))
                    .orElse("Suit not present");
        } else if(rank.isPresent()){
            return suit.map(s -> String.format("Return all the cards of rank %s", s))
                    .orElse("Rank not present");
        } else {
            return "All cards";
        }
    }


}
