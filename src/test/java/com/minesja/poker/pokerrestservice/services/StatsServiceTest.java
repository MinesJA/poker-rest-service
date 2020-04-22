package com.minesja.poker.pokerrestservice.services;

import com.minesja.poker.pokerrestservice.domains.Card;
import com.minesja.poker.pokerrestservice.utils.Rank;
import com.minesja.poker.pokerrestservice.utils.Suit;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class StatsServiceTest {

    @Test
    public void getStatsTest(){
        List<Card> cards2 = Arrays.asList(
            new Card(Suit.DIAMONDS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.THREE)
        );

        List<Card> cards5 = Arrays.asList(
            new Card(Suit.DIAMONDS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.THREE),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.FIVE),
            new Card(Suit.DIAMONDS, Rank.SIX)
        );


    }

}