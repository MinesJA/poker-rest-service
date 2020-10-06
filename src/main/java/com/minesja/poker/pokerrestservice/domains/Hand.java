package com.minesja.poker.pokerrestservice.domains;

import com.minesja.poker.pokerrestservice.utils.MetaHand;
import com.minesja.poker.pokerrestservice.utils.Suit;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * A Hand object is produced at every stage in the game, from the initial
 * dealing all the way to the river.
 *
 * cards represent all cards a player can actually use in their hand,
 * anywhere from 2 to 7 cards.
 *
 * bestHand represents the cards constituting the best possible hand in that combination.
 *
 * Kicker represents the highest card, not currently in the best hand.
 *
 * The MetHand represents the actual name of the bestHand. Absent an actual hand
 * (for example if only 2 cards are present and are not a pair) the MetaHand defaults to
 * High_Card.
 *
 * As an example, if a player has 2 total cards + the flop:
 *  2 of Clubs
 *  2 of Spades
 *  10 of Diamonds
 *  King of Clubs
 *  4 of Spades
 *
 *  Then the cards would include all 5, the bestHand would be the 2s
 *  and the kicker would be the King of clubs.
 */
public class Hand {

    public final List<Card> cards;
    public final List<Card> bestHand;
    public final Optional<Card> kicker;
    public final MetaHand metaHand;

    public Hand(List<Card> cards, List<Card> bestHand, Optional<Card> kicker, MetaHand metaHand) {
        this.cards = cards;
        this.bestHand = bestHand;
        this.kicker = kicker;
        this.metaHand = metaHand;
    }
}
