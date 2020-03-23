package com.minesja.poker.pokerrestservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private static final String template = "Card, %s!";

    @PostMapping("/game")
    @ResponseBody
    public String newGame(@RequestParam long playerCount) {
        return String.format("Created new came of %d players", playerCount);
    }

    @GetMapping("/game/{id}")
    @ResponseBody
    public String getGame(@PathVariable long id) {
        return String.format("Getting game with id %d gameId", id);
    }

    @GetMapping("/game/{id}/deal")
    @ResponseBody
    public String deal(@PathVariable long id) {
        return String.format("Dealing to game with id of %d", id);
    }
}
