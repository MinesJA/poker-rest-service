package com.minesja.poker.pokerrestservice.domains;


import com.minesja.poker.pokerrestservice.utils.Suit;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class DeckTest {

    @Test
    public void testDeckInit() {
        Deck deck = Deck.init();

        assertEquals(52, deck.cards.size());
        assertEquals(52, deck.cards.stream().distinct().collect(toList()).size());

        Map<Suit, ArrayList<Card>> cardsBySuit = deck.getCardsBySuit();

        for (Suit suit : cardsBySuit.keySet()) {
            assertEquals(13, cardsBySuit.get(suit).size());
        }
    }

    @Test
    public void testDeal() {

        Deck deck = Deck.init();
        List<Card> cards = dealCards(deck);

        assertEquals(52, cards.size());
        assertEquals(52, cards.stream().distinct().collect(toList()).size());
    }

    @Test
    public void testDealRandomness(){

        Deck deckA = Deck.init();
        Deck deckB = Deck.init();

        assertEquals(deckA.cards, deckB.cards);

        List<Card> cardsA = dealCards(deckA);
        List<Card> cardsB = dealCards(deckB);

        assertNotEquals(cardsA, cardsB);
    }

    @Test(expected = IllegalStateException.class)
    public void testDeckEmpty(){
        Deck deck = Deck.init();
        dealCards(deck,53);
    }

    private List<Card> dealCards(Deck deck){
        return dealCards(deck, 52);
    }

    private List<Card> dealCards(Deck deck, int upperBound){
        return IntStream.range(0, upperBound)
                .mapToObj(ignore -> deck.deal())
                .collect(toList());
    }
}