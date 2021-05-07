package runner;

import controller.GameController;
import exceptions.GeneralGameException;
import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
import exceptions.points.InvalidPointsException;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GameRunner {

    private final GameController gameController;
    private final List<Player> players;

    private final Logger GAME_RUNNER_LOGGER = LogManager.getLogger(GameRunner.class);

    private int round;
    private boolean roundWon = false;

    public GameRunner(List<Player> players) {
        gameController = new GameController(players);
        this.players = players;
        round = 6;
    }

    public void play() throws GeneralGameException {
        gameController.setupRound(round);
        while (!roundWon) {
            for (Player player : players) {
                playTurn(player);
                if (roundWon) break;
            }
        }
        roundWon = false;
        players.forEach(player -> player.setHasGoneDown(false));
        players.forEach(player -> player.setHasWon(false));
    }

    private void playTurn(Player player) throws InvalidPlayerException, InvalidDeckException, InvalidRoundException,
            InvalidHandException, InvalidCardException, InvalidPointsException {
        GAME_RUNNER_LOGGER.info("Upkeep step for " + player.getName());
        GAME_RUNNER_LOGGER.info(player + "'s hand: " + player.getHand().toString());

        gameController.drawCard(player, round);

        GAME_RUNNER_LOGGER.info("Discard step for " + player.getName());
        if (gameController.checkHandForWinCondition(player.getHand(), round) || player.getHasGoneDown()) {
            gameController.goDown(player, round);
            if (player.getHasWon()) {
                roundWon = true;
            } else {
                gameController.discardCard(player, round);
            }
        } else {
            gameController.discardCard(player, round);
        }

        if (player.getHasWon()) {
            roundWon = true;
        }

        if (roundWon) {
            GAME_RUNNER_LOGGER.info(player + " wins round " + round + "!");
            gameController.calculatePlayersPoints();
            printPoints();
            round++;
            return;
        }

        GAME_RUNNER_LOGGER.info(player + "'s hand: " + player.getHand().toString());
        System.out.println("===============================================================");
    }

    private void printPoints() {
        for (Player player : players) {
            GAME_RUNNER_LOGGER.info(player + "; points: " + player.getPoints());
        }
    }

}
