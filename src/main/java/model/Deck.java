package model;

import exceptions.InvalidCardException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {

    private List<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void makeDeck() throws InvalidCardException {
        int cardCount = 0;

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
        Card card = deck.get(0);
        deck.remove(0);

        return card;
    }

    public void reinitialize() throws InvalidCardException {
        makeDeck();
        shuffle();
    }

    public void dealToPlayer(Player player, int round) {
        IntStream.range(0, round).forEach($ ->
                dealCardToPlayer(player));
    }

    private void dealCardToPlayer(Player player) {
        player.getHand().addToHand(getCard());
    }
}
