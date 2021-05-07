package model;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.player.InvalidPlayerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {

    private List<Card> deck;
    private final Logger DECK_LOGGER = LogManager.getLogger(Deck.class);

    public Deck() {
        deck = new ArrayList<>();
    }

    public List<Card> getDeck() {
        return deck;
    }

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

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

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

    public void reinitialize() throws InvalidCardException {
        // deck contains 2 decks
        makeDeck();
        makeDeck();
        shuffle();
    }

    public void dealToPlayer(Player player, int round) throws InvalidPlayerException {
        if (player == null) {
            throw new InvalidPlayerException("Player can't be null");
        }

        DECK_LOGGER.info("Dealing to " + player + " " + round + " card(s)");

        IntStream.range(0, round).forEach($ ->
        {
            try {
                dealCardToPlayer(player);
            } catch (InvalidDeckException e) {
                e.printStackTrace();
            }
        });
    }

    private void dealCardToPlayer(Player player) throws InvalidDeckException {
        player.getHand().addToHand(getCard());
    }

}
