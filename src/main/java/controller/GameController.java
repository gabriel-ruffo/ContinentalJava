package controller;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
import model.Deck;
import model.Hand;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GameController {
    private final Deck deck;
    private final Deck discardPile;
    private final List<Player> players;
    private final HandAnalyzer handAnalyzer;

    private boolean discardCardHasBeenGrabbed = false;

    private final Logger GAME_CONTROLLER_LOGGER = LogManager.getLogger(GameController.class);

    public GameController(List<Player> players) {
        deck = new Deck();
        discardPile = new Deck();
        this.players = players;
        handAnalyzer = new HandAnalyzer();
    }
    /*
    TODO: Rules to implement
        1. Can't have a Tercia with more than one Joker
        2. Can't have a run with two Jokers next to each other
        3. Can't discard a Joker
     */
    public boolean checkHandForWinCondition(Hand hand, int round) throws InvalidCardException {
        switch (round) {
            case 6:
                return TerciaController.checkForTercias(hand, 2);
            case 7:
//                return checkForTerciaRun(hand);
            case 8:
                return RunController.checkForRuns(hand, 2);
            case 9:
                return TerciaController.checkForTercias(hand, 3);
            case 10:
//                return checkForTwoTerciasOneRun(hand);
            case 11:
//                return checkForOneTerciaTwoRuns(hand);
            case 12:
                return TerciaController.checkForTercias(hand, 4);
            case 13:
//                return checkForThreeRuns(hand);
            default:
//                throw new InvalidWinCondition(round + " is not a valid hand.");
        }
        return false;
    }

    public void setupRound(int round) throws InvalidPlayerException, InvalidRoundException, InvalidCardException {
        GAME_CONTROLLER_LOGGER.info("Setting up round: " + round);
        dealCards(round);
    }

    public void drawCard(Player player, int round) throws InvalidPlayerException, InvalidRoundException,
            InvalidDeckException, InvalidHandException, InvalidCardException {
        if (!discardCardHasBeenGrabbed && discardPile.getDeck().size() > 0) {
            if (!checkDiscardCardDesirability(player, round)) {
                deck.dealToPlayer(player, 1);
            }
        } else {
            deck.dealToPlayer(player, 1);
        }
        player.getHand().sortHand();
    }

    public void discardCard(Player player, int round) throws InvalidHandException, InvalidCardException {
        HandController handController = new HandController();
        discardPile.getDeck().add(
                handController.discardWorstCard(player.getHand(), round));
        discardCardHasBeenGrabbed = false;
    }

    public void goDown(Player player, int round) throws InvalidCardException {
        // generate tercia types
        // place down in order: overflow, perfect, incompletes with flex card completion
        // if overflow has 6+ cards and no perfect and no incompletes with flex card completion, split overflow
        // set hasGoneDown to true -- remember to turn to false when starting new round
        handAnalyzer.generateTerciaTypes(player.getHand());

        if (handAnalyzer.getPerfectTercias().size() == 2) {
            player.setHasGoneDown(true);
        } else if (handAnalyzer.getOverflowTercias().size() == 2) {
            player.setHasGoneDown(true);
        } else if (handAnalyzer.getIncompleteTercias().size() == 2 && handAnalyzer.getJokerCount(player.getHand()) == 2) {
            player.setHasGoneDown(true);
        }
    }

    private void dealCards(int round) throws InvalidCardException, InvalidPlayerException, InvalidRoundException {
        GAME_CONTROLLER_LOGGER.info("Dealing cards to players");
        deck.reinitialize();

        for (Player player : players) {
            deck.dealToPlayer(player, round);
            player.getHand().sortHand();
        }
    }

    private boolean checkDiscardCardDesirability(Player player, int round) throws InvalidDeckException, InvalidPlayerException,
            InvalidRoundException, InvalidHandException, InvalidCardException {
        for (Player drawPlayer : players) {
            if (handAnalyzer.cardHelpsPlayer(drawPlayer, discardPile.peekCard(), round)) {
                GAME_CONTROLLER_LOGGER.info(drawPlayer + " is grabbing " + discardPile.peekCard() + " from discard pile");
                discardPile.dealToPlayer(drawPlayer, 1);
                checkForOutOfTurn(drawPlayer, player);
                discardCardHasBeenGrabbed = true;
                return true;
            }
        }
        return false;
    }

    private void checkForOutOfTurn(Player drawPlayer, Player player) throws InvalidPlayerException, InvalidRoundException {
        if (!drawPlayer.equals(player)) {
            GAME_CONTROLLER_LOGGER.info(drawPlayer + " grabbed out of turn, dealing additional card from top deck");
            deck.dealToPlayer(drawPlayer, 1);
        }
    }

}
