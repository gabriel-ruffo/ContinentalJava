package controller;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
import model.Card;
import model.Deck;
import model.Hand;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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
        if (player.getHasGoneDown()) {
            checkDownedHands(player);
        }
        if (player.getHand().getHand().size() == 0) {
            player.setHasWon(true);
            discardCardHasBeenGrabbed = false;
            return;
        }
        discardPile.getDeck().add(handController.discardWorstCard(player.getHand(), round));
        discardCardHasBeenGrabbed = false;
    }

    /*
    Tests:
    player discard tercia possible to own downed hands
    player discard run possible to own downed hands
    player discard tercia possible to other player's downed hands
    player discard run possible to other player's downed hands
    player discard run possible to replace joker in other player's downed hands
    player discard run possible by relocating joker in other player's downed hands
        check weights & jokers for solution
    player discard tercia/run possibles where player discards whole hand
    player discard tercia/run possibles with one left over to be discarded to pile
     */
    private void checkDownedHands(Player player) {
        for (Player playerCheck : players) {
            if (playerCheck.equals(player))
                continue;
            if (playerCheck.getHasGoneDown()) {
                List<Hand> playerCheckDownedHands = playerCheck.getDownedHand();
                List<Hand> playerDownedHands = player.getDownedHand();
                Hand playerHand = player.getHand();


            }
        }
    }

    public void goDown(Player player, int round) throws InvalidCardException {
        // set hasGoneDown to true -- remember to turn to false when starting new round
        handAnalyzer.generateTerciaTypes(player.getHand());

        if (handAnalyzer.roundNeedsOnlyTercias(round)) {
            int neededTercias = round / 3;
            int validTerciasCount = handAnalyzer.getPerfectTercias().size() + handAnalyzer.getOverflowTercias().size();
            if (handAnalyzer.getIncompleteTercias().size() > 0 &&
                    handAnalyzer.getIncompleteTercias().size() == handAnalyzer.getJokerCount(player.getHand())) {
                validTerciasCount += handAnalyzer.getIncompleteTercias().size();
            }
            if (validTerciasCount >= neededTercias) {
                moveCardsToDownedHand(handAnalyzer, player);
                player.setHasGoneDown(true);
                if (player.getHand().getHand().size() == 0) {
                    player.setHasWon(true);
                }
            }
        }
    }

    private void moveCardsToDownedHand(HandAnalyzer handAnalyzer, Player player) {
        List<List<List<Card>>> allTerciaTypesList = new ArrayList<>();
        allTerciaTypesList.add(handAnalyzer.getOverflowTercias());
        allTerciaTypesList.add(handAnalyzer.getIncompleteTercias());
        allTerciaTypesList.add(handAnalyzer.getPerfectTercias());

        for (List<List<Card>> terciaTypeList : allTerciaTypesList) {
            for (List<Card> tercia : terciaTypeList) {
                player.getHand().removeCardsFromHand(tercia);
                player.getDownedHand().add(new Hand(tercia));
            }
        }

        player.getHand().removeCardsFromHand(handAnalyzer.getJokers(player.getHand()));
        player.getDownedHand().add(new Hand(handAnalyzer.getJokers(player.getHand())));
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
