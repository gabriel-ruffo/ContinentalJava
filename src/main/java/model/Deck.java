package model;

import java.util.Arrays;
import java.util.Collections;

public class Deck {

    private final Card[] deck;

    public Deck() {
        deck = new Card[54];
    }

    public Card[] getDeck() {
        return deck;
    }

    public void makeDeck() {
        int cardCount = 0;
        for (Suit suit : Suit.values()) {
            if (suit.equals(Suit.JOKER)) {
                break;
            }
            for (int i = 1; i <= 13; i++) {
                deck[cardCount++] = new Card(suit, i);
            }
        }

        deck[cardCount++] = new Card(Suit.JOKER, -1);
        deck[cardCount] = new Card(Suit.JOKER, -1);
    }

    public void shuffle() {
        Collections.shuffle(Arrays.asList(deck));
    }
}
