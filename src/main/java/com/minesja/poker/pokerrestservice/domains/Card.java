package com.minesja.poker.pokerrestservice.domains;

import com.minesja.poker.pokerrestservice.utils.Rank;
import com.minesja.poker.pokerrestservice.utils.Suit;

import java.util.Comparator;
import java.util.Objects;

public class Card {
    public final Suit suit;
    public final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public static Comparator<Card> RANK_ORDER = Comparator.comparingInt(c -> c.rank.score);

    public Suit getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card that = (Card) obj;
        return this.rank == that.rank
                && this.suit == that.suit;
    }
}
