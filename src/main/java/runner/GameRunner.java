package runner;

import controller.GameController;
import model.Deck;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameRunner {
    private final Logger logger = LogManager.getLogger(GameRunner.class);

    private final GameController gameController;
    private final List<Player> players;
    private final Deck deck;
    private Deck discardPile;
    private int round;

    public GameRunner() {
        gameController = new GameController();
        players = new ArrayList<>();
        deck = new Deck();
        discardPile = new Deck();
        round = 6;
    }

    public void play() {
        setupRound();
        for (Player player : players) {
            playTurn(player);
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void setupRound() {
        logger.info("Setting up round: " + round);
        dealCards();
    }

    private void playTurn(Player player) {
        /*
        1. Draw card from deck or discard (if one has not already been taken)
        2. Check for win condition
        2.a. If win: drop card groupings and discard if necessary
        2.b. If no win: discard card
         */
        drawCard(player);
        if (gameController.checkHandForWinCondition(player.getHand(), 6)) {
            // go down
            System.out.println(player.toString() + " wins round " + round + "!");
            round++;
        } else {
            // discard least useful card
            // keep track of useful/needed cards? during win condition checking?
        }
    }

    private void drawCard(Player player) {
        // if want discarded card
        // discardPile.dealToPlayer(player, 1);
        deck.dealToPlayer(player, 1);
        player.getHand().sortHand();
    }

    private void dealCards() {
        logger.info("Dealing cards to players");
        deck.reinitialize();

        for (Player player : players) {
            deck.dealToPlayer(player, round);
        }
    }

}
