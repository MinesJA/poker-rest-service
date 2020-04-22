package com.minesja.poker.pokerrestservice.cli;


import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.util.List;


@Component
public class RoundCommand implements Runnable {

    @Parameters
    private List<String> startingHand;


    @Override
    public void run() {
        System.out.println("Running round command" + startingHand.toString());
    }
}
