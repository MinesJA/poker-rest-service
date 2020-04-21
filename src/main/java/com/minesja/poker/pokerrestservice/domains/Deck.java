package com.minesja.poker.pokerrestservice.domains;

import com.minesja.poker.pokerrestservice.utils.Rank;
import com.minesja.poker.pokerrestservice.utils.Suit;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Deck {
    public final List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public static Deck init() {

        List<Card> cards = Arrays.stream(Suit.values()).flatMap(suit ->
                Arrays.stream(Rank.values())
                        .map(rank -> new Card(suit, rank))
        ).collect(toList());

        return new Deck(cards);
    }

    public Card deal() {
        if (cards.size() > 0) {
            int randomIndex = new Random().nextInt(cards.size());
            return cards.remove(randomIndex);
        } else {
            throw new IllegalStateException("Deck is empty. Create new deck.");
        }
    }

    public Map<Suit, ArrayList<Card>> getCardsBySuit() {
        return this.cards.stream()
                .collect(Collectors.toMap(
                        k -> k.suit,
                        v -> new ArrayList<>(Arrays.asList(v)),
                        (cardsA, cardsB) -> {
                            cardsA.addAll(cardsB);
                            return cardsA;
                        }
                ));
    }
}
