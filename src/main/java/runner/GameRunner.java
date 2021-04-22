package runner;

import controller.GameController;
import controller.HandAnalyzer;
import controller.HandController;
import exceptions.GeneralGameException;
import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
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
    private final Deck discardPile;
    private int round;
    private final HandAnalyzer handAnalyzer;
    private boolean discardCardHasBeenGrabbed = false;

    public GameRunner() {
        gameController = new GameController();
        players = new ArrayList<>();
        deck = new Deck();
        discardPile = new Deck();
        round = 6;
        handAnalyzer = new HandAnalyzer();
    }

    public void play() throws GeneralGameException {
        setupRound();
        for (Player player : players) {
            playTurn(player);
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void setupRound() throws InvalidCardException, InvalidPlayerException, InvalidRoundException {
        logger.info("Setting up round: " + round);
        dealCards();
    }

    private void playTurn(Player player) throws InvalidPlayerException, InvalidDeckException, InvalidRoundException,
            InvalidHandException, InvalidCardException {
        /*
        1. Draw card from deck or discard (if one has not already been taken)
        2. Check for win condition
        2.a. If win: drop card groupings and discard if necessary
        2.b. If no win: discard card
         */
        drawCard(player);
        if (gameController.checkHandForWinCondition(player.getHand(), round)) {
            // TODO: implement goDown() -- don't win unless all cards in hand are gone
            System.out.println(player + " wins round " + round + "!");
            round++;
        } else {
            // TODO: implement discardCard()
            discardCard(player);
            discardCardHasBeenGrabbed = false;
        }
    }

    private void discardCard(Player player) throws InvalidHandException, InvalidCardException {
        HandController handController = new HandController();
        discardPile.getDeck().add(
                handController.discardWorstCard(player.getHand(), round));
    }

    private void drawCard(Player player) throws InvalidPlayerException, InvalidDeckException, InvalidRoundException,
            InvalidHandException, InvalidCardException {
        if (!discardCardHasBeenGrabbed) {
            checkDiscardCardDesirability(player);
        } else {
            deck.dealToPlayer(player, 1);
        }
        player.getHand().sortHand();
    }

    private void checkDiscardCardDesirability(Player player) throws InvalidDeckException, InvalidPlayerException,
            InvalidRoundException, InvalidHandException, InvalidCardException {
        for (Player drawPlayer : players) {
            if (handAnalyzer.cardHelpsPlayer(drawPlayer, discardPile.getCard())) {
                discardPile.dealToPlayer(drawPlayer, 1);
                checkForOutOfTurn(drawPlayer, player);
                discardCardHasBeenGrabbed = true;
            }
        }
    }

    private void checkForOutOfTurn(Player drawPlayer, Player player) throws InvalidPlayerException, InvalidRoundException {
        if (!drawPlayer.equals(player)) {
            deck.dealToPlayer(drawPlayer, 1);
        }
    }

    private void dealCards() throws InvalidCardException, InvalidPlayerException, InvalidRoundException {
        logger.info("Dealing cards to players");
        deck.reinitialize();

        for (Player player : players) {
            deck.dealToPlayer(player, round);
        }
    }

}
