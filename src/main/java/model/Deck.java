package model;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.player.InvalidPlayerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> deck;
    private final Logger DECK_LOGGER = LogManager.getLogger(Deck.class);

    public Deck() {
        deck = new ArrayList<>();
    }

    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Creates one full deck with two jokers.
     * @throws InvalidCardException Thrown if a card is made with a null suit or invalid rank
     */
    public void makeDeck() throws InvalidCardException {
        for (Suit suit : Suit.values()) {
            if (suit.equals(Suit.JOKER)) {
                break;
            }
            for (int i = 1; i <= 13; i++) {
                deck.add(new Card(suit, i));
            }
        }

        deck.add(new Card(Suit.JOKER, -1));
        deck.add(new Card(Suit.JOKER, -1));
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Delete and return the next card in the deck.
     * @return the next card in queue
     * @throws InvalidDeckException Thrown if trying to remove a card from an empty deck
     */
    public Card getCard() throws InvalidDeckException {
        if (deck.isEmpty()) {
            throw new InvalidDeckException("Deck can't be empty");
        }

        Card card = deck.get(0);
        deck.remove(0);

        DECK_LOGGER.info("Dealt: " + card.toString());

        return card;
    }

    public Card peekCard() throws InvalidDeckException {
        if (deck.isEmpty()) {
            throw new InvalidDeckException("Deck is empty");
        }

        return deck.get(0);
    }

    /**
     * Reinitialize the deck between turns. Populates the main deck with two decks,
     * then shuffles.
     *
     * @throws InvalidCardException thrown when creating an invalid card
     */
    public void reinitialize() throws InvalidCardException {
        makeDeck();
        makeDeck();
        shuffle();
    }

    /**
     * Deal's number of cards equal to given round to the given player.
     * @param player Player to be dealt cards
     * @param round Number of cards to be dealt
     * @throws InvalidPlayerException Thrown if player is null
     * @throws InvalidDeckException Thrown if deck is empty
     */
    public void dealToPlayer(Player player, int round) throws InvalidPlayerException, InvalidDeckException {
        if (player == null) {
            throw new InvalidPlayerException("Player can't be null");
        }

        DECK_LOGGER.info("Dealing to " + player + " " + round + " card(s)");

        for (int i = 0; i < round; i++) {
            player.getHand().addToHand(getCard());
        }
    }

}
