package runner;

import controller.GameController;
import exceptions.GeneralGameException;
import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
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

    private final Logger LOG = LogManager.getLogger(GameRunner.class);

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

    /**
     * Main series of actions for each player's turn
     *      1. Draw a card
     *      2. Check if can go down
     *          2.1. Go down
     *          2.2. Check if won
 *          3. Discard
     * @param player Current player
     * @throws InvalidPlayerException Invalid player
     * @throws InvalidDeckException Invalid deck
     * @throws InvalidHandException Invalid hand
     * @throws InvalidCardException Invalid card
     * @throws InvalidPointsException Invalid point value
     */
    private void playTurn(Player player) throws InvalidPlayerException, InvalidDeckException,
            InvalidHandException, InvalidCardException, InvalidPointsException {
        LOG.info("Upkeep step for " + player.getName());
        LOG.info(player + "'s hand: " + player.getHand().toString());

        gameController.drawCard(player, round);

        if (gameController.checkHandForWinCondition(player.getHand(), round) && !player.getHasGoneDown()) {
            gameController.goDown(player, round);
            if (player.getHasWon()) {
                roundWonHelper(player);
                return;
            }
        }

        LOG.info("Discard step for " + player.getName());
        gameController.discardCard(player, round);

        if (player.getHasWon()) {
            roundWonHelper(player);
            return;
        }

        LOG.info(player + "'s hand: " + player.getHand().toString());
        System.out.println("===============================================================");
    }

    private void roundWonHelper(Player player) throws InvalidPointsException {
        roundWon = true;
        LOG.info(player + " wins round " + round + "!");
        gameController.calculatePlayersPoints();
        printPoints();
        round++;
    }

    private void printPoints() {
        for (Player player : players) {
            LOG.info(player + "; points: " + player.getPoints());
        }
    }

}
