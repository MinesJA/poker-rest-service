package com.minesja.poker.pokerrestservice.services;

import com.minesja.poker.pokerrestservice.domains.Card;
import com.minesja.poker.pokerrestservice.domains.Hand;
import com.minesja.poker.pokerrestservice.domains.Player;
import com.minesja.poker.pokerrestservice.domains.Table;
import com.minesja.poker.pokerrestservice.utils.MetaHand;
import com.minesja.poker.pokerrestservice.utils.Rank;
import com.minesja.poker.pokerrestservice.utils.Suit;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.minesja.poker.pokerrestservice.domains.Card.RANK_ORDER;
import static com.minesja.poker.pokerrestservice.utils.MetaHand.*;
import static com.minesja.poker.pokerrestservice.utils.Rank.TEN;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Hand Identification Service
 * <p>
 * Identifies best available hand
 */
@Service
public class IdentificationService {


    /**
     * Hands are identified by first determining how many cards are in the list.
     * A Two Pair, for example, only requires 2 cards, while a Full House requires 5.
     * <p>
     * Hands are then identified by checking 4 seperate patterns:
     * <p>
     * 1) Matching Ranks: Any hand related to ranks that match each other
     * Pair : 2 cards
     * Two Pair : 4 cards
     * Three of a Kind : 3 cards
     * Full House : 5 cards
     * Four of a Kind : 4 cards
     * <p>
     * 2) Ranks in sequence
     * Straight : 5
     * <p>
     * 3) Matching Suits
     * Flush : 5
     * <p>
     * 4) Ranks in sequence with Matching Suits
     * Straight Flush : 5
     * Royal Flush : 5
     *
     * @param cards List of cards that constitute a potential hand. Min of 1, max of 7.
     * @return List of possible hands
     */
    public List<Hand> identifyHand(List<Card> cards) throws Exception {

        List<Card> sortedByRank = cards.stream().sorted(RANK_ORDER).collect(toList());
        Map<Rank, List<Card>> collect = cards.stream().collect(groupingBy(Card::getRank));


        // 1 Royal Flush
        // 2 Straight Flush
        // 3 Four of a Kind X
        // 4 Full House X
        // 5 Flush
        // 6 Straight
        // 7 Three of a Kind X
        // 8 Two Pair X
        // 9 One Pair X
        // 10 High Card

        // Checks for:
        //      Pairs
        //      Three of a Kind
        //      Four of a Kind
        List<Hand> hands = collect.values()
                .stream()
                .filter(c -> c.size() > 1)
                .map(c -> {
                    int matchCount = c.size();

                    if (matchCount == 2) {
                        Optional<Card> kicker = sortedByRank.stream()
                                .filter(card -> card.rank != c.get(0).rank)
                                .max(RANK_ORDER);

                        cards.removeAll(c);

                        return new Hand(cards, c, kicker, TWO_PAIR);

                    } else if (matchCount == 3) {
                        Optional<Card> kicker = sortedByRank.stream()
                                .filter(card -> card.rank != c.get(0).rank)
                                .max(RANK_ORDER);

                        return new Hand(cards, c, kicker, THREE_KIND);

                    } else {
                        // Has to be 4 of a Kind
                        Optional<Card> kicker = sortedByRank.stream()
                                .filter(card -> card.rank != c.get(0).rank)
                                .max(RANK_ORDER);

                        return new Hand(cards, c, kicker, FOUR_KIND);
                    }
                }).collect(toList());


        // Checks for:
        //      Two Pair
        //      Full House
        List<Card> twoPairOrFullHouse = collect.values().stream()
                .filter(c -> c.size() == 2 || c.size() == 3)
                .flatMap(Collection::stream)
                .collect(toList());

        if (twoPairOrFullHouse.size() == 4) {
            Optional<Card> kicker = Stream.concat(cards.stream(), twoPairOrFullHouse.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, twoPairOrFullHouse, kicker, TWO_PAIR));
        } else if (twoPairOrFullHouse.size() == 5) {
            Optional<Card> kicker = Stream.concat(cards.stream(), twoPairOrFullHouse.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, twoPairOrFullHouse, kicker, FULL_HOUSE));
        }


        // Checks for:
        //      Flush
        //      Straight Flush
        //      Royal Flush
        Map<Suit, List<Card>> groupedBySuit = cards.stream().collect(groupingBy(Card::getSuit));

        List<Card> maybeFlush = groupedBySuit.values().stream()
                .filter(l -> l.size() == 5)
                .findFirst()
                .orElse(Collections.emptyList());

        if(!maybeFlush.isEmpty()){
            Optional<Card> kicker = Stream.concat(cards.stream(), maybeFlush.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, maybeFlush, kicker, FLUSH));

            if(isASequence(maybeFlush)){
                hands.add(new Hand(cards, maybeFlush, kicker, STRAIGHT_FLUSH));

                if(maybeFlush.get(0).rank == TEN){
                    hands.add(new Hand(cards, maybeFlush, kicker, ROYAL_FLUSH));
                }
            }
        }


        // Checks for:
        //      Straight
        List<Card> zeroToFour = sortedByRank.stream().skip(0).limit(4 - 0 + 1).collect(toList());
        List<Card> oneToFive = sortedByRank.stream().skip(0).limit(5 - 1 + 1).collect(toList());
        List<Card> twoToSix = sortedByRank.stream().skip(0).limit(6 - 2 + 1).collect(toList());

        if(zeroToFour.size() == 5 && isASequence(zeroToFour)){
            Optional<Card> kicker = Stream.concat(cards.stream(), zeroToFour.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, zeroToFour, kicker, STRAIGHT));
        } else if (oneToFive.size() == 5 && isASequence(oneToFive)){
            Optional<Card> kicker = Stream.concat(cards.stream(), oneToFive.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, oneToFive, kicker, STRAIGHT));
        } else if (twoToSix.size() == 5 && isASequence(twoToSix)) {
            Optional<Card> kicker = Stream.concat(cards.stream(), twoToSix.stream())
                    .distinct().max(RANK_ORDER);

            hands.add(new Hand(cards, twoToSix, kicker, STRAIGHT));
        }

        return hands;
    }



//    identifyByCount
//        switch (cards.size()) {
//        case 2:
//            hand = identifyDeal(cards);
//            break;
//        case 5:
//            hand = identifyFlop(cards);
//            break;
//        case 6:
//            hand = identifyTurn(cards);
//            break;
//        case 7:
//            hand = identifyRiver(cards);
//            break;
//        default:
//            throw new Exception(String.format("Players can only 2, 5, 6, 7 cards at a time. %d were given instead.", cards.size()));
//    }
//
//        return hand;

    private Hand identifyDeal(List<Card> cards) {
        assert cards.size() == 2;

        if (cards.get(0).rank == cards.get(1).rank) {
            return new Hand(cards, cards, Optional.empty(), TWO_PAIR);
        } else {
            List<Card> bestHand = cards.stream().max(RANK_ORDER).map(Arrays::asList).orElse(Collections.emptyList());
            return new Hand(cards, bestHand, Optional.empty(), HIGH_CARD);
        }
    }

    private Hand identifyFlop(List<Card> cards) {
        assert cards.size() == 5;

        cards.sort(RANK_ORDER);

        Boolean isSequence = isASequence(cards);

        if (isOneSuit(cards)) {
            // All same suit
            // Is at least FLUSH
            // Could be: STRAIGHT_FLUSH, ROYAL_FLUSH

            if (isSequence) {
                if (cards.get(0).rank.equals(TEN)) {
                    return new Hand(cards, cards, Optional.empty(), ROYAL_FLUSH);
                } else {
                    return new Hand(cards, cards, Optional.empty(), STRAIGHT_FLUSH);
                }
            } else {
                return new Hand(cards, cards, Optional.empty(), FLUSH);
            }

        } else {
            // Not all same suit, can't be Suit Based
            // Can only be: FOUR_KIND, FULL_HOUSE, STRAIGHT, THREE_KIND, TWO_PAIR, PAIR, HIGH_CARD

            if (isSequence) {
                // Must be STRAIGHT
                return new Hand(cards, cards, Optional.empty(), STRAIGHT);
            }

            Map<Rank, List<Card>> cardsByRank = cards.stream()
                    .collect(groupingBy(Card::getRank));

            List<List<Card>> sortedByNumRanks = cardsByRank.values().stream()
                    .sorted(comparing(List::size))
                    .collect(toList());

            int sizeFirst = sortedByNumRanks.get(0).size();

            if (sizeFirst == 4) {
                return new Hand(cards, sortedByNumRanks.get(0), Optional.of(sortedByNumRanks.get(1).get(0)), FOUR_KIND);
            } else if (sizeFirst == 3) {
                if (sortedByNumRanks.get(1).size() == 2) {
                    List<Card> bestHand = Stream.of(sortedByNumRanks.get(0), sortedByNumRanks.get(1))
                            .flatMap(Collection::stream)
                            .collect(toList());

                    return new Hand(cards, bestHand, Optional.empty(), FULL_HOUSE);
                } else {
                    Optional<Card> kicker = Stream.of(sortedByNumRanks.get(1), sortedByNumRanks.get(2))
                            .flatMap(Collection::stream)
                            .max(RANK_ORDER);

                    return new Hand(cards, sortedByNumRanks.get(0), kicker, THREE_KIND);
                }
            } else if (sizeFirst == 2) {
                if (sortedByNumRanks.get(1).size() == 2) {
                    List<Card> bestHand = Stream.of(sortedByNumRanks.get(0), sortedByNumRanks.get(1))
                            .flatMap(Collection::stream)
                            .collect(toList());

                    Optional<Card> kicker = Optional.of(sortedByNumRanks.get(3).get(0));

                    return new Hand(cards, bestHand, kicker, TWO_PAIR);
                } else {
                    Optional<Card> kicker = Stream.of(sortedByNumRanks.get(1), sortedByNumRanks.get(2), sortedByNumRanks.get(3))
                            .flatMap(Collection::stream)
                            .max(RANK_ORDER);

                    return new Hand(cards, sortedByNumRanks.get(0), kicker, PAIR);
                }
            } else {
                // HighCard

                List<Card> bestHand = cards.stream().max(RANK_ORDER).map(Arrays::asList).orElse(Collections.emptyList());

                return new Hand(cards, bestHand, Optional.empty(), HIGH_CARD);
            }

        }
    }

    private Hand identifyTurn(List<Card> cards) {
        assert cards.size() == 6;


        Map<Suit, List<Card>> cardsBySuit = cards.stream()
                .collect(groupingBy(Card::getSuit));

        Collection<List<Card>> values = cardsBySuit.values();

        Set<Suit> suits = cardsBySuit.keySet();

        suits.stream().map(suit -> {

            List<Card> x = cardsBySuit.get(suit);
            if (x.size() == 5) {
                return x;
            } else {

            }

        })


        Optional<List<Card>> first = cardsBySuit.values()
                .stream()
                .filter(l -> l.size() == 5)
                .findFirst();


        HashMap<Suit, List<Card>>

        for (Card card : cards) {

        }


//        cardsBySuit.values().stream()
//                .collect(partitioningBy(l -> l.size() == 5, Collector.of(x -> x)))
//                .values().stream().map(l -> l.stream().flatMap());

        if (first.isPresent()) {
            // Is Suit based, at least a Flush
            List<Card> sortedSuited = first.get().stream().sorted(RANK_ORDER).collect(toList());

            if (isASequence(sortedSuited)) {
                if (sortedSuited.get(0).rank == TEN) {


                    return new Hand(cards, sortedSuited, xx, ROYAL_FLUSH);
                } else {


                    return new Hand(cards, sortedSuited, xx, STRAIGHT_FLUSH);
                }
            }
            ;
        }


    }

    private Hand identifyRiver(List<Card> cards) {
        assert cards.size() == 7;

    }
//
//    private Hand identifyAny(List<Card> cards) {
//        return Hand(cards, )
//    }

//    static class Tuple {
//        int sizeListA;
//        int sizeListB;
//
//        static Tuple fourKind = Tuple.of(4, 1);
//        static Tuple fullHouse = Tuple.of(3, 2);
//        static Tuple threeKind = Tuple.of(3, 1);
//        static Tuple twoPair = Tuple.of(2, 2);
//        static Tuple pair = Tuple.of(2, 1);
//
//        private Tuple(int sizeListA, int sizeListB) {
//            this.sizeListA = sizeListA;
//            this.sizeListB = sizeListB;
//        }
//
//        public static Tuple of(Integer sizeA, Integer sizeB) {
//            return new Tuple(sizeA, sizeB);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(sizeListA, sizeListB);
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null || getClass() != obj.getClass()) return false;
//
//            Tuple that = (Tuple) obj;
//            return this.sizeListA == that.sizeListA
//                    && this.sizeListB == that.sizeListB;
//        }
//
//    }


//    private Hand identifyAll(List<Card> hand) {
//        Map<Suit, List<Card>> cardsBySuit = hand.stream()
//                .collect(groupingBy(Card::getSuit));
//
//
//        return cardsBySuit.values().stream()
//                .filter(l -> l.size() >= 5).findFirst()
//                .map(this::identifySuitBased)
//                .orElse(identifyRankBased(hand));
//
//                .map(l -> l.stream().sorted(RANK_ORDER).collect(toList()))
//                .map(l -> {
//                    Boolean isSeq = isASequence(l);
//
//                });
//
//
//        Optional<Card> kicker = hand.stream()
//                .filter(c -> !l.contains(c))
//                .max(RANK_ORDER);
//
//        if (isSeq) {
//            if (l.get(0).rank.equals(TEN)) {
//                return new Hand(l, kicker, ROYAL_FLUSH);
//            } else {
//                return new Hand(l, kicker, STRAIGHT_FLUSH;
//            }
//        } else {
//            return new Hand(l, kicker, FLUSH);
//        }
//    }


//    /**
//     * After grouping and sorting lists of matching cards, limit to biggest 2 lists.
//     * Then apply matching logic.
//     * <p>
//     * First 2 arrays after sorting are either going to be:
//     * [A,A], [B] = PAIR
//     * [A,A], [B,B] = TWO PAIR
//     * [A,A,A], [B] OR [B,B,B] = THREE OF A KIND
//     * [A,A,A], [B,B] = FULL HOUSE
//     * [A,A,A,A], [B] OR [B,B] OR [B,B,B] = Sum 5 = Four of a Kind
//     */
//    private Hand identifyRankBased(List<Card> cards) {
//
//        Map<Rank, List<Card>> cardsByRank = cards
//                .stream()
//                .collect(groupingBy(c -> c.rank, mapping(Function.identity(), toList())));
//
//        List<List<Card>> matchTest = cardsByRank
//                .values()
//                .stream()
//                .sorted((a, b) -> b.size() - a.size())
//                .limit(2)
//                .collect(toList());
//
//        List<Card> first = matchTest.get(0);
//        List<Card> second = matchTest.get(1);
//
//        Optional<MetaHand> maybeHand;
//
//        switch (first.size()) {
//            case 2:
//                maybeHand = second.size() == 1 ? Optional.of(PAIR) : Optional.of(TWO_PAIR);
//                break;
//            case 3:
//                maybeHand = second.size() == 2 ? Optional.of(FULL_HOUSE) : Optional.of(THREE_KIND);
//                break;
//            case 4:
//                maybeHand = Optional.of(FOUR_KIND);
//                break;
//            default:
//                maybeHand = Optional.empty();
//        }
//
//        return maybeHand;
//
//    }

//    private Optional<Card> getHighCard(List<Card> cards) {
//        return cards.stream().sorted(RANK_ORDER).findFirst();
//    }

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

    private Boolean isOneSuit(List<Card> cards) {
        assert cards.size() > 0;

        Card primary = cards.get(0);

        Optional<Card> suitOutlier = cards.stream()
                .filter(c -> c.suit != primary.suit)
                .findFirst();

        return !suitOutlier.isPresent();
    }


//    static class ChanceCard {
//
//        public final Optional<Suit> maybeSuit;
//        public final Optional<Rank> maybeRank;
//        public final Double chance;
//
//        public ChanceCard(Card card, Double chance) {
//            this(Optional.of(card.suit), Optional.of(card.rank), chance);
//        }
//
//        public ChanceCard(Optional<Suit> maybeSuit, Optional<Rank> maybeRank, Double chance) {
//            this.maybeSuit = maybeSuit;
//            this.maybeRank = maybeRank;
//            this.chance = chance;
//        }
//    }


//    public Hand identifyHands(Table table, Player player){
//        List<Card> tableCards = table.getAllCards();
//        List<Card> playerCards = player.cards;
//        List<Card> allCards = Stream.concat(tableCards.stream(), playerCards.stream()).collect(toList());
//
//        Card primary = allCards.get(0);
//
//        List<Card> sortedRank = allCards.stream()
//                .sorted(RANK_ORDER)
//                .collect(toList());
//
//
//
//
//        IntStream.range(0, 7)
//                .map(i -> {
//
//                    Card currentCard = sortedRank.get(i);
//                    int currentScore = currentCard.rank.score;
//
//                    if(i == 0) {
//                        return new ChanceCard(currentCard, 1d);
//                    }
//
//                    // If this rank is one more than the last rank
//                    int lastScore = sortedRank.get(i - 1).rank.score;
//                    if(currentScore == lastScore + 1){
//                        return new ChanceCard(currentCard, 1d);
//                    } else {
//                        return new ChanceCard()
//                    }
//                })
//
//
//
//
//    }


}
