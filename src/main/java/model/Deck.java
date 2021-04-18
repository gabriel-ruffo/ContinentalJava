package model;

import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.player.InvalidPlayerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {

    private final List<Card> deck;

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

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card getCard() throws InvalidDeckException {
        if (deck.isEmpty()) {
            throw new InvalidDeckException("Deck can't be empty");
        }

        Card card = deck.get(0);
        deck.remove(0);

        return card;
    }

    public void reinitialize() throws InvalidCardException {
        makeDeck();
        shuffle();
    }

    public void dealToPlayer(Player player, int round) throws InvalidPlayerException, InvalidRoundException {
        if (player == null) {
            throw new InvalidPlayerException("Player can't be null");
        }

        if (!roundIsValid(round)) {
            throw new InvalidRoundException("Round " + round + " is invalid. Must be between 6 and 13");
        }

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

    private boolean roundIsValid(int round) {
        return round >= 6 && round <= 13;
    }
}
