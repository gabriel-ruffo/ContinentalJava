package controller;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
import exceptions.points.InvalidPointsException;
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

    public void setupRound(int round) throws InvalidPlayerException, InvalidCardException {
        GAME_CONTROLLER_LOGGER.info("Setting up round: " + round);
        dealCards(round);
    }

    public void drawCard(Player player, int round) throws InvalidPlayerException,
            InvalidDeckException, InvalidHandException, InvalidCardException {
        GAME_CONTROLLER_LOGGER.info("Deck size: " + deck.getDeck().size());
        if (!discardCardHasBeenGrabbed && discardPile.getDeck().size() > 0) {
            if (!checkDiscardCardDesirability(player, round)) {
                deck.dealToPlayer(player, 1);
            }
        } else {
            deck.dealToPlayer(player, 1);
        }
        player.getHand().sortHand();
        checkIfDeckIsEmpty();
    }

    private void checkIfDeckIsEmpty() {
        if (deck.getDeck().size() == 1) {
            List<Card> tempDeck = discardPile.getDeck().subList(1, discardPile.getDeck().size());
            deck.getDeck().addAll(tempDeck);
            deck.shuffle();
        }
    }

    /**
     * Goes through the discard step of a player's turn. First checks
     * if player has gone down. If so, player checks own downed hands
     * and others' to discard extra cards. If in discarding to downed
     * hands, player's hand becomes empty, set win flags. Otherwise,
     * discard worst card, and check for an empty hand again.
     *
     * @param player Player to discard card(s)
     * @param round  Current round
     * @throws InvalidHandException On Hand is empty
     * @throws InvalidCardException On Card is invalid
     */
    public void discardCard(Player player, int round) throws InvalidHandException, InvalidCardException {
        HandController handController = new HandController();
        if (player.getHasGoneDown()) {
            checkDownedHands(player);
        }

        if (player.handIsEmpty()) {
            setWinFlags(player);
            return;
        }

        discardPile.getDeck().add(handController.discardWorstCard(player.getHand(), round));
        discardCardHasBeenGrabbed = false;

        if (player.handIsEmpty()) {
            setWinFlags(player);
        }
    }

    private void setWinFlags(Player player) {
        player.setHasWon(true);
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
        GAME_CONTROLLER_LOGGER.info("Checking downed hands");
        List<List<Hand>> handsList = new ArrayList<>();
        handsList.add(player.getDownedHand());
        Hand playerHand = player.getHand();

        for (Player playerCheck : players) {
            if (playerCheck.equals(player))
                continue;
            if (playerCheck.getHasGoneDown()) {
                handsList.add(playerCheck.getDownedHand());
            }
        }

        for (List<Hand> downedHands : handsList) {
            for (Hand hand : downedHands) {
                for (Card card : playerHand.getHand()) {
                    if (handAnalyzer.cardHelpsHand(card, hand)) {
                        GAME_CONTROLLER_LOGGER.info(card + " helps hand: " + hand);
                        player.getHand().removeFromHand(card);
                        hand.addToHand(card);
                        return;
                    }
                }
            }
        }
    }

    public void goDown(Player player, int round) throws InvalidCardException {
        // set hasGoneDown to true -- TODO: remember to turn to false when starting new round
        if (canGoDown(player, round)) {
            GAME_CONTROLLER_LOGGER.info(player + " is going down");
            moveCardsToDownedHand(handAnalyzer, player, round);
            player.setHasGoneDown(true);
            GAME_CONTROLLER_LOGGER.info(player.getName() + "'s downed hands: " + player.getDownedHand());
            if (player.handIsEmpty()) {
                player.setHasWon(true);
            }
        }
    }

    public boolean canGoDown(Player player, int round) throws InvalidCardException {
        handAnalyzer.generateTerciaTypes(player.getHand());
        if (handAnalyzer.roundNeedsOnlyTercias(round)) {
            int neededTercias = round / 3;
            int validTerciasCount = getValidTerciasCount(player.getHand());
            return validTerciasCount >= neededTercias;
        }

        return false;
    }

    public void calculatePlayersPoints() throws InvalidPointsException {
        for (Player player : players) {
            if (!player.getHand().getHand().isEmpty()) {
                player.addPoints(player.getHand().getPoints());
            }
        }
    }

    private int getValidTerciasCount(Hand hand) throws InvalidCardException {
        int validTerciasCount = handAnalyzer.getPerfectTercias().size() + handAnalyzer.getOverflowTercias().size();
        if (handAnalyzer.getIncompleteTercias().size() > 0 &&
                handAnalyzer.getIncompleteTercias().size() == handAnalyzer.getJokerCount(hand)) {
            validTerciasCount += handAnalyzer.getIncompleteTercias().size();
        }
        return validTerciasCount;
    }

    private void moveCardsToDownedHand(HandAnalyzer handAnalyzer, Player player, int round) throws InvalidCardException {
        List<List<List<Card>>> allTerciaTypesList = new ArrayList<>();
        List<List<Card>> incompleteTercias = handAnalyzer.getIncompleteTercias();
        if (validIncompleteTerciasWithJokers(incompleteTercias.size(), handAnalyzer.getJokerCount(player.getHand()))) {
            allTerciaTypesList.add(handAnalyzer.getIncompleteTercias());
        }

        allTerciaTypesList.add(handAnalyzer.getOverflowTercias());
        allTerciaTypesList.add(handAnalyzer.getPerfectTercias());

        int downedHandCount = 0;
        for (List<List<Card>> terciaTypeList : allTerciaTypesList) {
            for (List<Card> tercia : terciaTypeList) {
                player.getHand().removeCardsFromHand(tercia);
                player.getDownedHand().add(new Hand(tercia));
                if (++downedHandCount == round / 3) {
                    break;
                }
            }
        }
        if (validIncompleteTerciasWithJokers(incompleteTercias.size(), handAnalyzer.getJokerCount(player.getHand()))) {
            List<Card> jokers = handAnalyzer.getJokers(player.getHand());
            player.getHand().removeCardsFromHand(jokers);
            player.getDownedHand().add(new Hand(jokers));
        }
    }

    private boolean validIncompleteTerciasWithJokers(int incompleteTercias, int jokerCount) {
        return incompleteTercias > 0 && jokerCount == incompleteTercias;
    }

    private void dealCards(int round) throws InvalidCardException, InvalidPlayerException {
        GAME_CONTROLLER_LOGGER.info("Dealing cards to players");
        deck.reinitialize();

        for (Player player : players) {
            deck.dealToPlayer(player, round);
            player.getHand().sortHand();
        }
    }

    private boolean checkDiscardCardDesirability(Player player, int round) throws InvalidDeckException, InvalidPlayerException,
            InvalidHandException, InvalidCardException {
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

    private void checkForOutOfTurn(Player drawPlayer, Player player) throws InvalidPlayerException {
        if (!drawPlayer.equals(player)) {
            GAME_CONTROLLER_LOGGER.info(drawPlayer + " grabbed out of turn, dealing additional card from top deck");
            // give penalty card to discard pile drawing player
            deck.dealToPlayer(drawPlayer, 1);
            // give normal card to current turn player
            deck.dealToPlayer(player, 1);
        }
    }

}
