package com.minesja.poker.pokerrestservice;

import com.minesja.poker.pokerrestservice.cli.GameCommand;
import com.minesja.poker.pokerrestservice.cli.PokCommand;
import com.minesja.poker.pokerrestservice.cli.RoundCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class PokerRestServiceApplication implements CommandLineRunner {

	private PokCommand pokCommand;
	private GameCommand gameCommand;
	private RoundCommand roundCommand;

	public PokerRestServiceApplication(
			PokCommand pokCommand,
			GameCommand gameCommand,
			RoundCommand roundCommand) {
		this.pokCommand = pokCommand;
		this.gameCommand = gameCommand;
		this.roundCommand = roundCommand;
	}

	public static void main(String[] args) {
		SpringApplication.run(PokerRestServiceApplication.class, args);
	}

	@Override
	public void run(String... args){
		CommandLine commandLine = new CommandLine(pokCommand);
		commandLine.addSubcommand("game", gameCommand);
		commandLine.addSubcommand("round", roundCommand);

		commandLine.parseWithHandler(new CommandLine.RunLast(), args);
	}

}
