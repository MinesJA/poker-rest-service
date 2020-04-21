package com.minesja.poker.pokerrestservice.utils;


public enum Rank {
    TWO("TWO", 2),
    THREE("THREE", 3),
    FOUR("FOUR", 4),
    FIVE("FIVE", 5),
    SIX("SIX", 6),
    SEVEN("SEVEN", 7),
    EIGHT("EIGHT", 8),
    NINE("NINE", 9),
    TEN("TEN", 10),
    JACK("JACK", 11),
    QUEEN("QUEEN", 12),
    KING("KING", 13),
    ACE("ACE", 14);

    public final String rank;
    public final int score;

    Rank(String rank, int score) {
        this.rank = rank;
        this.score = score;
    }
}
