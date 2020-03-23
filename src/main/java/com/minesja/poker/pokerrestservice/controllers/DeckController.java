package com.minesja.poker.pokerrestservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DeckController {

    @PostMapping("/deck")
    @ResponseBody
    public String newDeck() {
        return "New shuffled deck with id 8";
    }

    @GetMapping("/deck/{id}")
    @ResponseBody
    public String getDeck(@PathVariable long id) {
        return String.format("Retrun entire deck of id %d", id);
    }

    @GetMapping("/deck/{id}/deal")
    @ResponseBody
    public String dealCard(@PathVariable long id, @RequestParam(required = false, defaultValue = "1") long cardCount) {
        return String.format("Deal %d card from deck with id of %d", cardCount, id);
    }

    @GetMapping("/deck/{id}/shuffle")
    @ResponseBody
    public String shuffleDeck(@PathVariable long id) {
        return String.format("Shuffling deck with id of %d", id);
    }


}
