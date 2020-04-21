package com.minesja.poker.pokerrestservice.utils;

public enum Suit {
    HEARTS("HEARTS", 10),
    CLUBS("CLUBS", 20),
    DIAMONDS("DIAMONDS", 30),
    SPADES("SPADES", 40);

    private final String suit;
    public final int score;

    Suit(String suit, int score) {
        this.suit = suit;
        this.score = score;
    }
}
