package com.minesja.poker.pokerrestservice.services;

import com.minesja.poker.pokerrestservice.domains.Card;
import com.minesja.poker.pokerrestservice.utils.Hand;
import com.minesja.poker.pokerrestservice.utils.Rank;
import com.minesja.poker.pokerrestservice.utils.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.minesja.poker.pokerrestservice.domains.Card.RANK_ORDER;
import static com.minesja.poker.pokerrestservice.utils.Hand.*;
import static com.minesja.poker.pokerrestservice.utils.Rank.TEN;
import static java.util.stream.Collectors.toList;

/**
 * Identifies best available hand
 */
public class HandService {


    /**
     * 1) Checks for any Suit based hands
     *  Includes: Royal Flush, Straight Flush, Flush,
     * <p>
     * 2) Checks for any matching based hands
     *  Includes: Four of a Kind, Full House, Three of a Kind,
     *      Two Pair, One Pair
     * <p>
     * 3) Checks for any order based hands (non-suit)
     *  Includes: Straight
     * <p>
     * 4) Checks for high card
     *
     * @param cards
     * @return
     */
    public Hand identifyHand(List<Card> cards) {
//        List<Hand> suitOrder = Hand.by(SUIT_ORDER);
//        List<Hand> rankOrder = Hand.by(RANK_ORDER);
//
//        List<Card> fiveRepeating = groupByRank
//                .values()
//                .stream()
//                .sorted((a, b) -> b.size() - a.size())
//                .flatMap(Collection::stream)
//                .limit(5)
//                .collect(toList());


        identifySuit(cards);
        identifyMatching(cards);
        identifyStraight(cards);



        Hand bestHand = identifySuit(cards).stream()
                .orElse(() -> identifyMatching(cards))
                .orElse(() -> identifyStraight(cards))
                .getOrElse(() -> identifyHighCard(cards));

        Optional<Hand> maybeHand = identifySuit(cardsBySuit);

        // Checks for hand where matching is involved


        // Check for Order
        identifyStraight(cards);

        identifyHighCard(cards);



        return
    }


    /**
     * After grouping and sorting lists of matching cards, limit to biggest 2 lists.
     * Then apply matching logic.
     * <p>
     * First 2 arrays after sorting are either going to be:
     * [A,A], [B] = PAIR
     * [A,A], [B,B] = TWO PAIR
     * [A,A,A], [B] OR [B,B,B] = THREE OF A KIND
     * [A,A,A], [B,B] = FULL HOUSE
     * [A,A,A,A], [B] OR [B,B] OR [B,B,B] = Sum 5 = Four of a Kind
     */
    private Optional<Hand> identifyMatching(List<Card> cards) {

        Map<Rank, List<Card>> cardsByRank = cards
                .stream()
                .collect(Collectors.groupingBy(c -> c.rank, Collectors.mapping(Function.identity(), toList())));

        List<List<Card>> matchTest = cardsByRank
                .values()
                .stream()
                .sorted((a, b) -> b.size() - a.size())
                .limit(2)
                .collect(toList());

        List<Card> first = matchTest.get(0);
        List<Card> second = matchTest.get(1);

        Optional<Hand> maybeHand;

        switch (first.size()) {
            case 2:
                maybeHand = second.size() == 1 ? Optional.of(PAIR) : Optional.of(TWO_PAIR);
                break;
            case 3:
                maybeHand = second.size() == 2 ? Optional.of(FULL_HOUSE) : Optional.of(THREE_KIND);
                break;
            case 4:
                maybeHand = Optional.of(FOUR_KIND);
                break;
            default:
                maybeHand = Optional.empty();
        }

        return maybeHand;

    }

    /**
     * Returns best suit based hand.
     *  In order:
     * 1) Royal Flush
     * 2) Straight Flush
     * 3) Flush
     *
     * @param cardsBySuit
     * @return
     */
    private Optional<Hand> identifySuit(List<Card> cards) {
        Map<Suit, List<Card>> cardsBySuit = cards
                .stream()
                .collect(Collectors.groupingBy(c -> c.suit, Collectors.mapping(Function.identity(), toList())));

        return cardsBySuit
                .values()
                .stream()
                .filter(l -> l.size() >= 5).findFirst()
                .map(l -> l.stream().sorted(RANK_ORDER).collect(toList()))
                .map(l -> {
                    Boolean isSeq = isASequence(l);

                    if (isSeq) {
                        if (l.get(0).rank.equals(TEN)) {
                            return ROYAL_FLUSH;
                        } else {
                            return STRAIGHT_FLUSH;
                        }
                    } else {
                        return FLUSH;
                    }
                });
    }

    private Optional<Hand> identifyStraight(List<Card> cards) {
        List<Card> sortedByRank = cards.stream()
                .sorted(RANK_ORDER)
                .collect(toList());

        cards.stream()
                .sorted(RANK_ORDER)
                .mapToLong(i ->)
                .collect(toList());


        return isASequence(sortedByRank) ? Optional.of(STRAIGHT) : Optional.empty();
    }

    /**
     * Must take list of cards in sequential order by Rank
     *
     * @param orderedCards
     * @return
     */
    private Boolean isASequence(List<Card> orderedCards) {

        int seqIndicator = IntStream.range(0, orderedCards.size())
                .reduce((val, i) -> {

                    if (val == -1) return val;

                    if (i > orderedCards.size() - 1) return i;

                    Card cardA = orderedCards.get(val);
                    Card cardB = orderedCards.get(i);

                    if (cardB.rank.score - cardA.rank.score == 1) {
                        return ++val;
                    } else {
                        return -1;
                    }

                }).getAsInt();

        return seqIndicator > 0;
    }
}
