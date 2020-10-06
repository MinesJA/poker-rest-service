package com.minesja.poker.pokerrestservice.utils;


public enum MetaHand {

    ROYAL_FLUSH("Royal flush", "A, K, Q, J, 10, all the same suit.", 10), // 5 of same suit
    STRAIGHT_FLUSH("Straight flush", "Five cards in a sequence, all in the same suit.", 9), // 5 of same suit
    FOUR_KIND("Four of a kind", "All four cards of the same rank.", 8), // Match on
    FULL_HOUSE("Full house", "Three of a kind with a pair.", 7), // Match on
    FLUSH("Flush", "Any five cards of the same suit, but not in a sequence.", 6), // 5 of same suit
    STRAIGHT("Straight", "Five cards in a sequence, but not of the same suit.", 5), // Order of rank
    THREE_KIND("Three of a kind", "Three cards of the same rank.", 4), // Match on
    TWO_PAIR("Two pair", "Two different pairs.", 3), // Match on
    PAIR("Pair", "Two cards of the same rank.", 2), // Match on
    HIGH_CARD("High Card", "When you haven't made any of the hands above, the highest card plays.", 1);

    public final String name;
    public final String description;
    public final int score;

    MetaHand(String name, String description, int score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }
}
