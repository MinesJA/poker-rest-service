package com.minesja.poker.pokerrestservice.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.*;

import java.util.List;


@Component
public class GameCommand implements Runnable {

    @Spec
    CommandSpec spec;

    @Option(names = {"-n", "--new"})
    private Integer numPlayers;

    @Override
    public void run(){
        spec.commandLine().get
//        System.out.println("Number of players"  + "|" + numPlayers.toString());
    }

}
