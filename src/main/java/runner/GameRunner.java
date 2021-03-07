package runner;

import controller.GameController;
import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GameRunner {
    private GameController gameController;
    private List<Player> players;
    private Deck deck;
    private int round;

    public GameRunner() {
        gameController = new GameController();
        players = new ArrayList<>();
        deck = new Deck();
        round = 6;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setupRound() {
        dealCards();
    }

    private void dealCards() {
        deck.reinitialize();

        for (Player player : players) {
            IntStream.range(0, round).forEach($ ->
                    dealCardToPlayer(player));
        }
    }

    private void dealCardToPlayer(Player player) {
        player.getHand().addToHand(deck.getCard());
    }

}
