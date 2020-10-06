package com.minesja.poker.pokerrestservice.services;

import com.minesja.poker.pokerrestservice.domains.Card;
import com.minesja.poker.pokerrestservice.utils.MetaHand;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.minesja.poker.pokerrestservice.utils.MetaHand.FLUSH;
import static com.minesja.poker.pokerrestservice.utils.Rank.*;
import static com.minesja.poker.pokerrestservice.utils.Suit.*;
import static junit.framework.TestCase.assertEquals;

public class HandIdentServiceTest {


    private IdentificationService handIdent;

    @Before
    public void init() {
        handIdent = new IdentificationService();
    }


    @Test
    public void testIdentifyRoyalFlush(){

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TEN),
                new Card(SPADES, QUEEN),
                new Card(CLUBS, FOUR),
                new Card(SPADES, JACK),
                new Card(SPADES, ACE)
        );

        MetaHand identified = handIdent.identifyHand(hand);

        assertEquals(FLUSH, identified);
    }

    @Test
    public void testIdentifyStraightFlush(){

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);
    }

    @Test
    public void testIdentifyFourKind(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyFullHouse(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyFlush(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyStraight(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyThreeKind(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyTwoPair(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyPair(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

    @Test
    public void testIdentifyHighCard(){
        HandIdentificationService handIdentification = new HandIdentificationService();

        List<Card> hand = Arrays.asList(
                new Card(SPADES, KING),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, SIX),
                new Card(SPADES, THREE),
                new Card(CLUBS, FOUR),
                new Card(SPADES, FOUR),
                new Card(CLUBS, TWO)
        );

        MetaHand identified = handIdentification.identifyHand(hand);

        assertEquals(FLUSH, identified);

    }

}