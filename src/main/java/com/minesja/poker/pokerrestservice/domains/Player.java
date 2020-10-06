package com.minesja.poker.pokerrestservice.domains;

import java.util.List;

public class Player {

    public final List<Card> cards;

    public Player(List<Card> cards) {
        assert cards.size() == 2;
        this.cards = cards;
    }
}
