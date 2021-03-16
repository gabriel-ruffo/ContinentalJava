package model;

import exceptions.InvalidCardException;

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

    public Card getCard() {
        if (deck.isEmpty()) {
            // throw new empty deck exception
        }

        Card card = deck.get(0);
        deck.remove(0);

        return card;
    }

    public void reinitialize() throws InvalidCardException {
        makeDeck();
        shuffle();
    }

    public void dealToPlayer(Player player, int round) {
        if (player == null) {
            // throw new invalid player exception
        }

        if (!roundIsValid(round)) {
            // throw new invalid round exception
        }
        IntStream.range(0, round).forEach($ ->
                dealCardToPlayer(player));
    }

    private void dealCardToPlayer(Player player) {
        player.getHand().addToHand(getCard());
    }

    private boolean roundIsValid(int round) {
        return round >= 6 && round <= 13;
    }
}
