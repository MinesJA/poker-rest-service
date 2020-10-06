package com.minesja.poker.pokerrestservice.domains;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.EMPTY_LIST;

public class Table {

    public final List<Card> flop;
    public final List<Card> turn;
    public final List<Card> river;

    public Table(List<Card> flop) {
        this(flop, EMPTY_LIST, EMPTY_LIST);
    }

    public Table(List<Card> flop, List<Card> turn) {
        this(flop, turn, EMPTY_LIST);
    }

    public Table(List<Card> flop, List<Card> turn, List<Card> river) {
        assert flop.size() == 3;
        assert turn.size() == 1;
        assert river.size() == 1;

        this.flop = flop;
        this.turn = turn;
        this.river = river;
    }

    public List<Card> getAllCards(){
        return Stream.of(this.flop, this.turn, this.river)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
