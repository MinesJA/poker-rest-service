package com.minesja.poker.pokerrestservice.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
    name = "pok",
    description = "Main poker entry point"
)
@Component
public class PokCommand implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new PokCommand(), args);
    }

    @Override
    public void run() {
        System.out.println("The main entry for Poker CLI");
    }
}


//
//
//    @Parameters
//    private List<String> numPlayers;
//
//    @Option(names = {"-g", "game"})
//
//    public static void main(String[] args) {
//        CommandLine.run(new CLI(), args);
//    }
//
//
//    @Command(name = "game")
//    public void StartGameCommand (){
//        System.out.println("Starting game");
//    }
//
//    @Command(name = "round")
//    public void startRoundCommand(){
//        System.out.println("Starting round");
//    }
//
//    @Command(name = "flop")
//    public void addFlopCommand(){
//        System.out.println("Adding flop");
//    }
//
//    @Command(name = "4th")
//    public void AddFourthCommand(){
//        System.out.println("Adding fourth card");
//    }
//
//    @Command(name = "5th")
//    public void AddFifthCommand(){
//        System.out.println("Adding fifth card");
//    }
//
//
//
